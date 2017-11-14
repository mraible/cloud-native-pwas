package com.example.carcatalogservice

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.support.beans
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@SpringBootApplication
@EnableDiscoveryClient
class CarCatalogServiceApplication

fun main(args: Array<String>) {

    SpringApplicationBuilder()
            .sources(CarCatalogServiceApplication::class.java)
            .initializers(beans {
                bean {
                    ApplicationRunner {
                        val repository = ref<CarRepository>()
                        listOf("Ferrari", "Alfa Romeo", "Jaguar", "Porsche", "Lamborghini",
                                "Bugatti", "AMC Gremlin", "Triumph Stag", "Ford Pinto", "Yugo GV", "Hummer H2")
                                .forEach { repository.save(Car(name = it)) }
                        repository.findAll().forEach { println(it) }
                    }
                }
            })
            .run(*args)
}

@RestController
class CarRestController(private val carRepository: CarRepository) {

    @GetMapping("/cars")
    fun cars() = this.carRepository.findAll()
}

interface CarRepository : JpaRepository<Car, Long>

@Entity
data class Car(@Id @GeneratedValue var id: Long? = null, var name: String? = null)