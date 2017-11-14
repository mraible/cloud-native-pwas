package com.example.carcatalogservice

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
class CarCatalogServiceApplication

fun main(args: Array<String>) {

    SpringApplicationBuilder()
            .sources(CarCatalogServiceApplication::class.java)
            .initializers(beans {
                bean {
                    ApplicationRunner {
                        val carsRepo = ref<CarRepository>()
                        val map: Publisher<Car> = listOf("Ferrari", "Alfa Romeo", "Jaguar", "Porsche", "Lamborghini",
                                "Bugatti", "AMC Gremlin", "Triumph Stag", "Ford Pinto", "Yugo GV", "Hummer H2")
                                .toFlux()
                                .flatMap { carsRepo.save(Car(name = it)) }
                        carsRepo
                                .deleteAll()
                                .thenMany(map)
                                .thenMany(carsRepo.findAll())
                                .subscribe { println(it) }
                    }
                }
                bean {
                    router {
                        GET("/cars") { ServerResponse.ok().body(ref<CarRepository>().findAll()) }
                    }
                }
            })
            .run(*args)
}

@Document
data class Car(@Id var id: String? = null, var name: String? = null)

interface CarRepository : ReactiveMongoRepository<Car, String>