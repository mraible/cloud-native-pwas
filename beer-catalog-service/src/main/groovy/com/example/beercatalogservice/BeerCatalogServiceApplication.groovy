package com.example.beercatalogservice

import groovy.transform.Canonical
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.annotation.Bean
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@EnableDiscoveryClient
@SpringBootApplication
class BeerCatalogServiceApplication {

    @Bean
    ApplicationRunner init(BeerRepository br) {
        return { args ->
            ["a", "b", "c"].forEach { n -> br.save(new Beer(name: n)) }
            br.findAll().forEach { println(it) }
        }
    }

    static void main(String[] args) {
        SpringApplication.run BeerCatalogServiceApplication, args
    }
}

@RestController
class BeerController {

    @Autowired
    BeerRepository repository

    @GetMapping("/beers")
    def beers() {
        return repository.findAll()
    }
}

interface BeerRepository extends JpaRepository<Beer, Long> {}

@Canonical
@Entity
class Beer {

    @Id
    @GeneratedValue
    Long id

    String name
}