package top.roothk.serviceweather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServiceWeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceWeatherApplication.class, args);
    }
}
