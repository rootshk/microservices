package top.roothk.serviceoss.Utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class JSONUtils {

    /**
     * 用于RESTful返回JSONObjcet对象
     * @param code 错误码
     * @param msg 信息
     * @param data 返回数据
     * @return
     */
    public JSONObject getRoot(int code, String msg, Object data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error_code", code);
        jsonObject.put("msg", msg);
        jsonObject.put("data", data);
        return jsonObject;
    }
}
