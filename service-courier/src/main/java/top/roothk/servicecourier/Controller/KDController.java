package top.roothk.servicecourier.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.roothk.servicecourier.Enums.CourierCodeEnums;
import top.roothk.servicecourier.Utils.KdniaoTrackQueryAPI;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class KDController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping(value = "/demo")
    public String demo() {
        JSONArray jsonArray = JSONArray.parseArray(stringRedisTemplate.opsForValue().get("kdConfig"));
        log.info("size = " + jsonArray.size());
        Random random = new Random();
        log.info("random = " + random);
        Integer in = random.nextInt(jsonArray.size());
        return jsonArray.getJSONObject(in).toString();
    }

    private boolean is(String code){
        for (CourierCodeEnums c : CourierCodeEnums.values()) {
            if (c.name().equals(code)) {
                return true;
            }
        }
        return false;
    }

    @GetMapping(value = "/query")
    public String get(@RequestParam(value = "code") String code,@RequestParam(value = "number") String number) {
        //转大写
        code = code.toUpperCase();
        //是否存在
        boolean is = is(code);
        if(!is){
            return "该快递商暂时不支持";
        }
        if(null == number){
            return "单号为空";
        }
        //redis有没有已查询过的物流信息，默认为一小时更新一次
        is = stringRedisTemplate.hasKey(code+number);
        if(is){
            return stringRedisTemplate.opsForValue().get(code+number);
        }
        JSONArray jsonArray = JSONArray.parseArray(stringRedisTemplate.opsForValue().get("kdConfig"));
        Random random = new Random();
        Integer in = random.nextInt(jsonArray.size());
        KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI(jsonArray.getJSONObject(in));
        try {
            String result = api.getOrderTracesByJson(code, number);
            stringRedisTemplate.opsForValue().set(code+number,result,60,TimeUnit.MINUTES);//设置为有效期60分钟
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }
}
