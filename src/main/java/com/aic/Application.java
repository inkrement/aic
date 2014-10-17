package com.aic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    /**
     * Starts an embedded Tomcat server instance which is reachable under
     * http://localhost:8080.
     *
     * @param args
     */
    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }
}
