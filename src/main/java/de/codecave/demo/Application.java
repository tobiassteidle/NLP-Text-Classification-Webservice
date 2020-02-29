package de.codecave.demo;

import de.codecave.demo.component.impl.LemmatizerCoreNLP;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        LemmatizerCoreNLP.selfcheck();
        SpringApplication.run(Application.class, args);
    }

}