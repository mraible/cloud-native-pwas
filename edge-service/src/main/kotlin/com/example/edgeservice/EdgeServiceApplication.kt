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
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.core.Ordered
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter


@EnableZuulProxy
@EnableCircuitBreaker
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
class EdgeServiceApplication {

    @Bean
    open fun simpleCorsFilter(): FilterRegistrationBean {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedOrigins = listOf("http://localhost:4200")
        config.allowedMethods = listOf("GET", "POST", "PATCH");
        config.allowedHeaders = listOf("*")
        source.registerCorsConfiguration("/**", config)
        val bean = FilterRegistrationBean(CorsFilter(source))
        bean.order = Ordered.HIGHEST_PRECEDENCE
        return bean
    }
}

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