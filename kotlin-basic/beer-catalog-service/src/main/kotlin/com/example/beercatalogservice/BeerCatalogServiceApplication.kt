package com.example.beercatalogservice

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.context.support.beans
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.rest.core.annotation.RepositoryRestResource
import org.springframework.web.bind.annotation.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@SpringBootApplication
@EnableDiscoveryClient
class BeerCatalogServiceApplication

fun main(args: Array<String>) {

    SpringApplicationBuilder()
            .sources(BeerCatalogServiceApplication::class.java)
            .initializers(beans {
                bean {
                    ApplicationRunner {
                        val repository = ref<BeerRepository>()
                        listOf("PBR", "Coors Light", "Kronenbourg", "Budweiser", "Heineken", "Sapporo",
                                "Tsingtao", "Rochefort", "Star Lager", "Castle Lager", "Nomad Jet Lag IPA",
                                "Brahma", "Patagonia", "Bierschmiede Amboss")
                                .forEach { repository.save(Beer(name = it)) }
                        repository.findAll().forEach { println(it) }
                    }
                }
            })
            .run(*args)
}

@RestController
class BeerRestController(private val beerRepository: BeerRepository) {

    @GetMapping("/beers")
    fun beers() = this.beerRepository.findAll()

    @PostMapping("/beers")
    @CrossOrigin(origins = arrayOf("*"))
    fun add(@RequestBody beer: Beer): Beer {
        this.beerRepository.save(beer)
        return beer;
    }
}

@RepositoryRestResource
@CrossOrigin(origins = arrayOf("*"))
interface BeerRepository : JpaRepository<Beer, Long>

@Entity
data class Beer(@Id @GeneratedValue var id: Long? = null, var name: String? = null)