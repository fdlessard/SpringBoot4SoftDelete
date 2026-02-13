package io.fdlessard.codebites.orders;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBoot4SoftDeleteApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot4SoftDeleteApplication.class, args);
    }

    @Bean
    public String checkJpa(ApplicationContext ctx) {
        String[] beans = ctx.getBeanNamesForType(EntityManagerFactory.class);
        System.out.println("DEBUG: Found " + beans.length + " EntityManagerFactories");
        return "ok";
    }
}
