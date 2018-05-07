package top.roothk.servicecourier.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.roothk.servicecourier.Enums.CourierCodeEnums;
import top.roothk.servicecourier.Utils.JSONUtils;
import top.roothk.servicecourier.Utils.KdniaoTrackQueryAPI;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class KDController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    JSONUtils jsonUtils;

    @GetMapping(value = "/demo", produces = "application/json;charset=UTF-8")
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

    /**
     * 快递鸟的输出接口
     * @param code
     * @param number
     * @return
     */
    @GetMapping(value = "/kdn/query", produces = "application/json;charset=UTF-8")
    public JSONObject get(@RequestParam(value = "code") String code, @RequestParam(value = "number") String number) {
        //转大写
        code = code.toUpperCase();
        //是否存在
        boolean is = is(code);
        if(!is){
            return jsonUtils.getRoot(1,"该快递商暂时不支持",null);
        }
        if(null == number){
            return jsonUtils.getRoot(1,"单号为空",null);
        }
        //redis有没有已查询过的物流信息，默认为一小时更新一次
        //有可能出现空指针
        is = stringRedisTemplate.hasKey(code+number);
        if(is){
            JSONObject o = JSONObject.parseObject(stringRedisTemplate.opsForValue().get(code+number));
            return jsonUtils.getRoot(0,"ok",o);
        }
        JSONArray jsonArray = JSONArray.parseArray(stringRedisTemplate.opsForValue().get("kdConfig"));
        //随机使用API密钥分摊接口调用次数
        Random random = new Random();
        Integer in = random.nextInt(jsonArray.size());
        KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI(jsonArray.getJSONObject(in));
        try {
            String result = api.getOrderTracesByJson(code, number);
            stringRedisTemplate.opsForValue().set(code+number,result,60,TimeUnit.MINUTES);//设置为有效期60分钟
            return jsonUtils.getRoot(0,"ok",JSONObject.parseObject(result));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonUtils.getRoot(1,"error",null);
    }

    /**
     * 快递鸟：提供给公司的快递接口
     * @param code
     * @param number
     * @return
     */
    @GetMapping(value = "/kdn/query/flc", produces = "application/json;charset=UTF-8")
    public JSONObject getFlc(@RequestParam(value = "code") String code,
                             @RequestParam(value = "number") String number) {
        //转大写
        code = code.toUpperCase();
        //是否存在
        boolean is = is(code);
        if(!is){
            return jsonUtils.getRootObj(1,"该快递商暂时不支持",null);
        }
        if(null == number){
            return jsonUtils.getRootObj(1,"单号为空",null);
        }
        //redis有没有已查询过的物流信息，默认为一小时更新一次
        //有可能出现空指针
        is = stringRedisTemplate.hasKey("flc:" + code+number);
        if(is){
            return jsonUtils.getRootObj(1,"ok",JSONObject.parseObject(stringRedisTemplate.opsForValue().get("flc:" + code+number)));
        }
        JSONArray apiConfig = JSONArray.parseArray(stringRedisTemplate.opsForValue().get("kdConfig"));
        //随机使用API密钥分摊接口调用次数
        Random random = new Random();
        Integer in = random.nextInt(apiConfig.size());
        KdniaoTrackQueryAPI api = new KdniaoTrackQueryAPI(apiConfig.getJSONObject(in));
        try {
            String result = api.getOrderTracesByJson(code, number);
            log.info("------>>" + result);
            //拿到快递信息
            //要输出的信息
            JSONObject a = new JSONObject();
            //物流信息
            JSONArray b = new JSONArray();
            JSONObject jsonObject = JSONObject.parseObject(result);
            log.info("------>>" + jsonObject);
            if(null != jsonObject.getString("State")){
                String state = jsonObject.getString("State");
                if(state.equals("2") || state.equals("3") || state.equals("4")){
                    a.put("message","ok");
                    //得到运输数据
                    JSONArray data = jsonObject.getJSONArray("Traces");
                    if(data.size() > 0){//物流信息不为空
                        for(Integer i = 0; i < data.size(); i++){
                            JSONObject obj = data.getJSONObject(data.size()-i-1);
                            JSONObject objtob = new JSONObject();
                            objtob.put("time",obj.get("AcceptTime"));
                            objtob.put("context",obj.get("AcceptStation"));
                            b.add(objtob);
                        }
                        a.put("data",b);
                    }
                }
            }

            stringRedisTemplate.opsForValue().set("flc:" + code+number,a.toString(),60,TimeUnit.MINUTES);//设置为有效期60分钟
            return a;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonUtils.getRootObj(1,"error",null);
    }

}
