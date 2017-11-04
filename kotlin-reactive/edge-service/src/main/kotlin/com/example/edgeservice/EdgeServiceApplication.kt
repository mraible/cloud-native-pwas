package com.example.edgeservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerExchangeFilterFunction
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator
import org.springframework.cloud.gateway.handler.predicate.RoutePredicates.path
import org.springframework.cloud.gateway.route.gateway
import org.springframework.cloud.netflix.hystrix.HystrixCommands
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux

@EnableDiscoveryClient
@SpringBootApplication
class EdgeServiceApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder()
            .sources(EdgeServiceApplication::class.java)
            .initializers(beans {
                bean {
                    WebClient.builder().filter(ref<LoadBalancerExchangeFilterFunction>()).build()
                }
                bean {
                    DiscoveryClientRouteDefinitionLocator(ref<DiscoveryClient>())
                }
                bean {
                    gateway {
                        route {
                            id("custom-edge-reservations")
                            predicate(path("/beers"))
                            uri("lb://beer-catalog-service/beers")
                        }
                    }
                }
                bean {
                    router {
                        val bad = listOf("Coors Light", "PBR", "Budweiser", "Heineken")

                        val client = ref<WebClient>()

                        GET("/good-beers") {

                            val beers = client
                                    .get()
                                    .uri("http://beer-catalog-service/beers")
                                    .retrieve()
                                    .bodyToFlux(Beer::class.java)
                                    .filter { !bad.contains(it.name) }
                            val failureReadyBeers = HystrixCommands
                                    .from(beers)
                                    .commandName("beers")
                                    .fallback(Flux.empty())
                                    .eager()
                                    .build()
                            ServerResponse.ok().body(failureReadyBeers)
                        }
                    }
                }
            })
            .run(*args)
}

data class Beer(var id: String? = null, var name: String? = null)