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
import org.springframework.http.HttpMethod
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import reactor.core.publisher.toMono

@EnableDiscoveryClient
@SpringBootApplication
class EdgeServiceApplication

fun main(args: Array<String>) {
    SpringApplicationBuilder()
            .sources(EdgeServiceApplication::class.java)
            .initializers(beans {
                bean {
                    /*
                    config.setAllowedOrigins(listOf("domain1.com", "domain2.com"));
                    config.setAllowedMethods(listOf("GET", "POST"));
                    config.setAllowedHeaders(listOf("header1", "header2"));
                    config.setExposedHeaders(listOf("header3", "header4"));
                    config.setMaxAge(123L);
                    config.setAllowCredentials(false);*/
                    val config = CorsConfiguration()
                    CorsWebFilter({ config.applyPermitDefaultValues() })
                }
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
                            predicate(path("/cars"))
                            uri("lb://car-catalog-service/cars")
                        }
                    }
                }
                bean {
                    router {
                        val bad = listOf("AMC Gremlin", "Triumph Stag", "Ford Pinto", "Yugo GV", "Hummer H2")

                        val client = ref<WebClient>()

                        GET("/good-cars") {

                            val cars = client
                                    .get()
                                    .uri("http://car-catalog-service/cars")
                                    .retrieve()
                                    .bodyToFlux(Car::class.java)
                                    .filter { !bad.contains(it.name) }
                            val failureReadyCars = HystrixCommands
                                    .from(cars)
                                    .commandName("cars")
                                    .fallback(Flux.empty())
                                    .eager()
                                    .build()
                            ServerResponse.ok().body(failureReadyCars)
                        }
                    }
                }
            })
            .run(*args)
}

data class Car(var id: String? = null, var name: String? = null)