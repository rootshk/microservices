package top.roothk.serviceimages.Config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


/**
 * 有多个CommandLineRunner接口时用@Order(value=1)可以指定执行顺序（小的先执行）
 * CommandLineRunner表示在所有的bean以及applicationCOntenxt完成后的操作
 */
@Slf4j
@Component
public class OnConfig implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        try {
            //具体初始化
            log.info("------------------------- >> Spring Boot 初始化完成 >>");
        } catch (Exception e) {
            //错误处理
        }
    }
}