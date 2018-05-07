package top.roothk.servicecourier.Utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
@Component
public class KD100QueryAPI {

    public String get(String uri) {
        RestTemplate restTemplate = new RestTemplate();
//            uri + "?id=" + key + "&com=" + com + "&nu=" + nu + "&order=desc")

        String msg = restTemplate.getForObject(uri,String.class);
//        String msg = restTemplate.getForObject(
//                "http://api.kuaidi100.com/api?id=3bef603438b72b63&com=wanxiangwuliu&nu=604018966088750", String.class);
        return msg;
    }
}
