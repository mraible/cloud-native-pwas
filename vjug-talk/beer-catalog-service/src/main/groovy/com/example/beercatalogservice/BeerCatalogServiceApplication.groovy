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

    static void main(String[] args) {
        SpringApplication.run BeerCatalogServiceApplication, args
    }

    @Bean
    ApplicationRunner init(BeerRepository repository) {
        return {
            args ->
                ["PBR", "Coors Light", "Kronenbourg", "Budweiser", "Heineken", "Sapporo", "Tsingtao", "Rochefort", "Star Lager",
                 "Castle Lager", "Nomad Jet Lag IPA", "Brahma", "Patagonia", "Bierschmiede Amboss"]
                        .forEach { repository.save(new Beer(name: it)) }
                repository.findAll().forEach { println(it) }
        }
    }
}

@RestController
class BeerRestController {

    @Autowired
    BeerRepository repository

    @GetMapping("/beers")
    def beer() {
        return repository.findAll()
    }
}

interface BeerRepository extends JpaRepository<Beer, Long> {}

@Entity
@Canonical
class Beer {

    @Id
    @GeneratedValue
    Long id

    String name
}