package top.roothk.servicecalculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EnableAutoConfiguration
public class ServiceCalculationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCalculationApplication.class, args);
    }
}
