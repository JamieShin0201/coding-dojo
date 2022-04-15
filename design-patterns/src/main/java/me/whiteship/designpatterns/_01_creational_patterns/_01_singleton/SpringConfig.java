package me.whiteship.designpatterns._01_creational_patterns._01_singleton;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.Persistence;

@Configuration
public class SpringConfig {

    @Bean
    public String hello() {
        Persistence.createEntityManagerFactory()
        return "hello";
    }

}
