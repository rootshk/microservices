package top.roothk.testmysql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@EnableAutoConfiguration
@SpringBootApplication
@MapperScan("top.roothk.servicecalculation.Service.Mapper")
public class TestMysqlApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestMysqlApplication.class, args);
    }
}
