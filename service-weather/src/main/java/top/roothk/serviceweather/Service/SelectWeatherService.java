package top.roothk.serviceweather.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import top.roothk.serviceweather.Utils.JSONUtils;
import top.roothk.serviceweather.Utils.WeatherQueryAPI;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class SelectWeatherService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    JSONUtils jsonUtils;

    public JSONObject selectWeatherMsg(String select, String code) {
        Boolean is = stringRedisTemplate.hasKey(code);
        if(is)
            return JSONObject.parseObject(stringRedisTemplate.opsForValue().get(code));
        JSONArray wConfig = JSONArray.parseArray(stringRedisTemplate.opsForValue().get("weatherConfig"));
        WeatherQueryAPI api = new WeatherQueryAPI();
        Random random = new Random();
        Integer in = random.nextInt(wConfig.size());
        JSONObject config = wConfig.getJSONObject(in);
        //查询
        JSONObject msg = api.query(
                config.getString("AppCode"),
                config.getString("url"),
                config.getString("query"),
                select,
                code);
        //判断是否有查询成功
        for(int i = 0; i < 5; i++){
            if(msg != null){
                break;
            } else {
                msg = api.query(
                        config.getString("AppCode"),
                        config.getString("url"),
                        config.getString("query"),
                        select,
                        code);
            }
        }
        if(null != msg && msg.getString("status").equals("0")){
            //成功查询了,就存在redis里面，有效期三分钟
            stringRedisTemplate.opsForValue().set(code,msg.toString(),3,TimeUnit.MINUTES);//三分钟有效期
        }
        return jsonUtils.getRoot(0, "成功", msg);
    }
}
