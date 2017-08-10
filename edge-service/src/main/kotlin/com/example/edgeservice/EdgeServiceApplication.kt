package com.example.edgeservice

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@EnableZuulProxy
@EnableCircuitBreaker
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
class EdgeServiceApplication

fun main(args: Array<String>) {
    SpringApplication.run(EdgeServiceApplication::class.java, *args)
}

@FeignClient("beer-catalog-service")
interface CraftBeerClient {

    @GetMapping("/beers")
    fun read(): Array<Beer>
}

@RestController
class CraftBeerApiAdapter(val client: CraftBeerClient) {

    fun goodBeersFallback(): Collection <Map<String, String>> = arrayListOf()

    @GetMapping("/good-beers")
    @HystrixCommand(fallbackMethod = "goodBeersFallback")
    fun goodBeers(): Collection<Map <String, String?>> {
        return client
                .read()
                .filter { it.name != "b" }
                .map { mapOf("name" to it.name) }
    }
}

data class Beer(var name: String? = null, var id: Long? = null)