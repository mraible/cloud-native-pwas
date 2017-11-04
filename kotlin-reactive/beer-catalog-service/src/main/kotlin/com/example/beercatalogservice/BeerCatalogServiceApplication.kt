package com.example.beercatalogservice

import org.reactivestreams.Publisher
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.support.beans
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.toFlux

@SpringBootApplication
@EnableDiscoveryClient
class BeerCatalogServiceApplication

fun main(args: Array<String>) {

    SpringApplicationBuilder()
            .sources(BeerCatalogServiceApplication::class.java)
            .initializers(beans {
                bean {
                    ApplicationRunner {
                        val beersRepo = ref<BeerRepository>()
                        val map: Publisher<Beer> = listOf("PBR", "Coors Light", "Kronenbourg", "Budweiser", "Heineken", "Sapporo",
                                "Tsingtao", "Rochefort", "Star Lager", "Castle Lager", "Nomad Jet Lag IPA",
                                "Brahma", "Patagonia", "Bierschmiede Amboss")
                                .toFlux()
                                .flatMap { beersRepo.save(Beer(name = it)) }
                        beersRepo
                                .deleteAll()
                                .thenMany(map)
                                .thenMany(beersRepo.findAll())
                                .subscribe { println(it) }
                    }
                }
                bean {
                    router {
                        GET("/beers") { ServerResponse.ok().body(ref<BeerRepository>().findAll()) }
                    }
                }
            })
            .run(*args)
}

@Document
data class Beer(@Id var id: String? = null, var name: String? = null)

interface BeerRepository : ReactiveMongoRepository<Beer, String>