package com.example.demo

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

@SpringBootApplication
@EnableAutoConfiguration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
@Controller
class DemoApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<DemoApplication>(*args)
        }
    }

    @GetMapping("/", "/page1")
    fun getView1(): Mono<String> = Mono.just("template1")

	@GetMapping("/page2")
	fun getView2(): Mono<String> = Mono.just("template2")

	@GetMapping("/page3")
	fun getView3(): Mono<String> = Mono.just("template3")

    @Bean
    @ConditionalOnMissingBean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        http.authorizeExchange {
            it!!.pathMatchers("/oauth2/**").permitAll()
                .pathMatchers("/", "/*").authenticated()
        }
        http.oauth2Login {}
        http.logout { it!!.logoutUrl("/logout") }
        return http.build()
    }

}