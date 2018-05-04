package top.roothk.serviceweather.Utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.HashMap;

public class WeatherQueryAPI {

    @Autowired
    JSONUtils jsonUtils;

    /**
     *
     * @param appcode 查询凭证
     * @param url 请求地址
     * @param path 请求方法
     * @param select 查询方式
     * @param code 查询条件信息
     * @return
     */
    public static JSONObject query( String appcode, String url, String path, String select, String code) {
        String method = "GET";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);

        Map<String, String> querys = new HashMap<String, String>();
        switch (select){
            case "city"://中文名查询
                querys.put("city", code);
                break;
            case "citycode"://城市代码查询
                querys.put("citycode", code);
                break;
            case "cityid"://城市ID查询
                querys.put("cityid", code);
                break;
            case "ip"://ip查询
                querys.put("ip", code);
                break;
            case "location"://本机查询
                querys.put("location", code);
                break;
            default:
                break;
        }

        try {
            HttpResponse response = HttpUtils.doGet(url, path, method, headers, querys);
//            return response.toString();
            //获取response的body
            return JSONObject.parseObject(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
