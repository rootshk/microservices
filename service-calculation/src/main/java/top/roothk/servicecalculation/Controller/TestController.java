package top.roothk.servicecalculation.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class TestController {

    //将从远程配置获取app.hello这个key
    @Value("${app.hello}")
    String bar;

    //根据配置的不同输出不同
    @GetMapping("/")
    public String hello(){
        return "This is " + bar;
    }
}
