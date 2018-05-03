package top.roothk.servicecourier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServiceCourierApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCourierApplication.class, args);
    }
}
