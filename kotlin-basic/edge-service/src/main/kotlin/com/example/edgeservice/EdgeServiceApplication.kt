package com.example.edgeservice

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.feign.EnableFeignClients
import org.springframework.cloud.netflix.feign.FeignClient
import org.springframework.cloud.netflix.zuul.EnableZuulProxy
import org.springframework.context.annotation.Bean
import org.springframework.core.Ordered
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@EnableCircuitBreaker
@EnableFeignClients
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
class EdgeServiceApplication {

    @Bean
    fun simpleCorsFilter(): FilterRegistrationBean<CorsFilter> {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.allowedOrigins = listOf("*")
        config.allowedMethods = listOf("*");
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

@FeignClient("car-catalog-service")
interface CarClient {

    @GetMapping("/cars")
    fun read(): Array<Car>
}


@RestController
class CarApiAdapterRestController(val carClient: CarClient) {

    fun fallback(): Collection<Car> = arrayListOf()

    @HystrixCommand(fallbackMethod = "fallback")
    @GetMapping("/good-cars")
    fun goodCars(): Collection<Car> =
            carClient
                    .read()
                    .filter { !arrayOf("AMC Gremlin", "Triumph Stag", "Ford Pinto", "Yugo GV", "Hummer H2").contains(it.name) }

}

class Car(var id: Long? = null, var name: String? = null)