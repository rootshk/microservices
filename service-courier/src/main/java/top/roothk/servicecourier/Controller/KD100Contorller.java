package top.roothk.servicecourier.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.roothk.servicecourier.Utils.JSONUtils;
import top.roothk.servicecourier.Utils.KD100QueryAPI;
import top.roothk.servicecourier.Utils.KdniaoTrackQueryAPI;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
public class KD100Contorller {

    @Autowired
    JSONUtils jsonUtils;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     * 快递100接口
     * http://api.kuaidi100.com/api?id=3bef603438b72b63&com=wanxiangwuliu&nu=604018966088750
     * @param code
     * @param number
     * @return
     */
    @GetMapping(value = "/100/query")
    public JSONObject get100(@RequestParam(value = "code") String code, @RequestParam(value = "number") String number) {
        if(null == number || null == code){
            return jsonUtils.getRoot(1,"单号或快递公司代码为空",null);
        }
        //redis有没有已查询过的物流信息，默认为一小时更新一次
        Boolean is = stringRedisTemplate.hasKey("100:" + code+number);
        if(is){
            JSONObject o = JSONObject.parseObject(stringRedisTemplate.opsForValue().get("100:" + code+number));
            return jsonUtils.getRoot(0,"ok",o);
        }
        JSONArray jsonArray = JSONArray.parseArray(stringRedisTemplate.opsForValue().get("kdConfig"));
        //随机使用API密钥分摊接口调用次数
        Random random = new Random();
        Integer in = random.nextInt(jsonArray.size());
        KD100QueryAPI api = new KD100QueryAPI();
        try {
            String result = api.get(jsonArray.getJSONObject(in),code, number);
            stringRedisTemplate.opsForValue().set("100:" + code+number,result,60,TimeUnit.MINUTES);//设置为有效期60分钟
            return jsonUtils.getRoot(0,"ok",JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonUtils.getRoot(1,"error",null);
    }
}
