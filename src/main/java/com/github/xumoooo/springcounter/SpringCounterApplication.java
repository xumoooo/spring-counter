package com.github.xumoooo.springcounter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


@Import(SecurityConfiguration.class)
@SpringBootApplication
public class SpringCounterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCounterApplication.class, args);
    }

    @Bean
    CounterHandlerExceptionResolver getCustomHandlerExceptionResolver() {
        return new CounterHandlerExceptionResolver();
    }

    @Bean
    EmbeddedServletContainerCustomizer EmbeddedServletContainerCustomizer() {
        return  new CounterEmbeddedServletContainerCustomizer();
    }
}
