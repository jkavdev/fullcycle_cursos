package br.com.jkavdev.fullcycle.admin.catalogo.infrastructure;

import br.com.jkavdev.fullcycle.admin.catalogo.infrastructure.configuration.WebserverConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(WebserverConfig.class, args);
    }
}
