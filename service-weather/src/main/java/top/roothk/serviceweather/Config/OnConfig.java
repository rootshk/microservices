package top.roothk.serviceweather.Config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 有多个CommandLineRunner接口时用@Order(value=1)可以指定执行顺序（小的先执行）
 * CommandLineRunner表示在所有的bean以及applicationContenxt完成后的操作
 */
@Component
public class OnConfig implements CommandLineRunner {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public void run(String... args) throws Exception {

        /* 配置天气接口 - 开始 */
        //查看Redis是否已经设置有天气API配置
        Boolean kdConfig = stringRedisTemplate.hasKey("weatherConfig");
        if (!kdConfig) {
            JSONObject a = new JSONObject();
            a.put("query", "/weather/query");
            a.put("AppKey", "24873840");
            a.put("AppSecret", "305dabc4b4ad360c7f6ca28d3098d9a1");
            a.put("AppCode", "0c213eda439c4ba6b30a64223d2dcc1c");
            a.put("url", "http://jisutqybmf.market.alicloudapi.com");
            JSONObject b = new JSONObject();
            b.put("query", "/weather/query");
            b.put("AppKey", "24873840");
            b.put("AppSecret", "305dabc4b4ad360c7f6ca28d3098d9a1");
            b.put("AppCode", "0c213eda439c4ba6b30a64223d2dcc1c");
            b.put("url", "http://jisutqybmf.market.alicloudapi.com");
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(a);
            jsonArray.add(b);
            stringRedisTemplate.opsForValue().set("weatherConfig", jsonArray.toString());
        }
        /* 配置天气接口 - 结束 */

        /* 初始化城市信息 */
        }
}