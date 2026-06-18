package org.example.tahadaw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TahadawApplication {

    public static void main(String[] args) {
        SpringApplication.run(TahadawApplication.class, args);
    }

}
