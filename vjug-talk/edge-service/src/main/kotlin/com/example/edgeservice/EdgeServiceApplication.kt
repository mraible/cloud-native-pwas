package com.example.edgeservice

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@EnableCircuitBreaker
@EnableFeignClients
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
class EdgeServiceApplication

fun main(args: Array<String>) {
    SpringApplication.run(EdgeServiceApplication::class.java, *args)
}

@FeignClient("beer-catalog-service")
interface BeerClient {

    @GetMapping("/beers")
    fun read(): Array<Beer>
}


@RestController
class BeerApiAdapterRestController(val beerClient: BeerClient) {

    fun fallback(): Collection <Beer> = arrayListOf()

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/good-beers")
    fun goodBeers(): Collection<Beer> =
            beerClient
                    .read()
                    .filter { !arrayOf("Coors Light", "PBR", "Budweiser", "Heineken").contains(it.name) }

}

class Beer(var id: Long? = null, var name: String? = null)