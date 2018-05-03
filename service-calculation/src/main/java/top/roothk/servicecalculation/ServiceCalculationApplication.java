package top.roothk.servicecalculation;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@RefreshScope
@EnableEurekaClient
@EnableAutoConfiguration
@SpringBootApplication
@MapperScan("top.roothk.servicecalculation.Service.Mapper")
public class ServiceCalculationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceCalculationApplication.class, args);
    }
}
