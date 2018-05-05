package top.roothk.servicecourier.Config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 有多个CommandLineRunner接口时用@Order(value=1)可以指定执行顺序（小的先执行）
 * CommandLineRunner表示在所有的bean以及applicationContenxt完成后的操作
 */
@Slf4j
@Component
public class OnConfig implements CommandLineRunner {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(String... args) throws Exception {

        /* 配置快递接口 - 开始 */
        //查看Redis是否已经设置有快递的eid，key配置
        Boolean kdConfig = stringRedisTemplate.hasKey("kdConfig");
        if (!kdConfig) {
            JSONObject a = new JSONObject();
            a.put("id", "roothk1");
            a.put("eid", "1338587");
            a.put("key", "fe843159-ae88-4d02-bf15-24ab35249249");
            a.put("url", "http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx");
            a.put("100key", "3bef603438b72b63");
            a.put("100url", "http://api.kuaidi100.com/api");
            JSONObject b = new JSONObject();
            b.put("id", "roothk2");
            b.put("eid", "1338587");
            b.put("key", "fe843159-ae88-4d02-bf15-24ab35249249");
            b.put("url", "http://api.kdniao.cc/Ebusiness/EbusinessOrderHandle.aspx");
            b.put("100key", "3bef603438b72b63");
            b.put("100url", "http://api.kuaidi100.com/api");
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(a);
            jsonArray.add(b);
            stringRedisTemplate.opsForValue().set("kdConfig", jsonArray.toString());
        }
        /* 配置快递接口 - 结束 */
        log.info("------------------------- >> Spring Boot 初始化完成 >>");
    }
}