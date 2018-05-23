package top.roothk.serviceweather.Controller;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import top.roothk.serviceweather.Service.SelectWeatherService;
import top.roothk.serviceweather.Utils.HttpUtils;
import top.roothk.serviceweather.Utils.IPUtils;
import top.roothk.serviceweather.Utils.JSONUtils;
import top.roothk.serviceweather.Utils.SMSTimedPushUtils;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/")
public class WeatherController {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    JSONUtils jsonUtils;

    @Autowired
    IPUtils ipUtils;

    @Autowired
    SelectWeatherService selectWeatherMsg;

    @Autowired
    SMSTimedPushUtils smsTimedPushUtils;

    @GetMapping(value = "demo")
    @CrossOrigin(methods = { RequestMethod.GET }, origins = "*")
    public String demo() {
        return "demo";
    }

//    private boolean is(String code){
//        for (WeatherCodeEnums c : WeatherCodeEnums.values()) {
//            if (c.name().equals(code)) {
//                return true;
//            }
//        }
//        return false;
//    }
    @GetMapping(value = "ip", produces = "application/json;charset=UTF-8")
    @CrossOrigin(methods = { RequestMethod.GET }, origins = "*")
    public JSONObject getIP(HttpServletRequest request){
        HttpUtils ip = new HttpUtils();
        String ips = null;
        try {
            ips = ip.getIpAddr(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonUtils.getRoot(0,"ok",ips);
    }

    @CrossOrigin(methods = { RequestMethod.GET }, origins = "*")
    @GetMapping(value = "query", produces = "application/json;charset=UTF-8")
    public JSONObject getToIP(@RequestParam(value = "ip",required = false,defaultValue = "ip") String ip,
                              @RequestParam(value = "city",required = false,defaultValue = "city") String city,
                              @RequestParam(value = "cityid",required = false,defaultValue = "cityid") String cityCode) {
        String select = "";//查询方式
        String code = "";//查询条件
        if(!ip.equals("ip")){
            //查询IP位置的天气
            Boolean is = ipUtils.isIP(ip);
            if(is){
                select = "ip";
                code = ip;
            } else {
                return jsonUtils.getRoot(1,"错误，该IP地址填写错误",ip);
            }
        } else if (!city.equals("city")) {
            //按照城市名称来查询天气，如果有同名则查询API提供商列表最先出现的
//            Boolean is = is(city);
//            if(is){
                select = "city";
                code = city;
//            } else {
//                return jsonUtils.getRoot(1,"错误，该城市不存在",city);
//            }
        } else if (!cityCode.equals("cityid")) {
            //按照城市代码查询天气，确保精确查询
            //判断是否为存数字
            boolean is = cityCode.matches("[0-9]+");
            if(is){
                select = "cityid";
                code = cityCode;
            } else {
                return jsonUtils.getRoot(1,"错误，该城市代码非纯数字",cityCode);
            }
        } else {
            return jsonUtils.getRoot(1,"错误，未输入任何查询条件",null);
        }
//        return jsonUtils.getRoot(1,"错误，未填入任何符合的条件参数",city);

        //正式开始！！！
        return selectWeatherMsg.selectWeatherMsg(select,code);
    }

    @CrossOrigin(methods = { RequestMethod.GET }, origins = "*")
    @GetMapping(value = "sms", produces = "application/json;charset=UTF-8")
    public JSONObject sms(){
        String s = null;
        s = smsTimedPushUtils.toPush("13288993990");
        return jsonUtils.getRoot(1,"error",s);
    }
}
