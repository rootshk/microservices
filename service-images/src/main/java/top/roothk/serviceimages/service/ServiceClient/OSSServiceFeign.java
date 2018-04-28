package top.roothk.serviceimages.service.ServiceClient;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import top.roothk.serviceimages.service.ServiceClient.Hystrix.OSSServiceFeignHystrix;

@Component
@FeignClient(value = "service-oss",fallbackFactory = OSSServiceFeignHystrix.class)
public interface OSSServiceFeign {

    /**
     * 获取文件地址,不存在则发送错误信息
     * @param eid
     * @param sid
     * @param format
     * @return
     */
    @RequestMapping(value = "/img/{eid}/{sid}",method = RequestMethod.GET)
    JSONObject getImg(@PathVariable(value = "eid") String eid,
                      @PathVariable(value = "sid") String sid,
                      @RequestParam(value = "format", defaultValue = "png") String format);

    /**
     * 上传文件,如果文件已存在则返回错误信息
     * @param base64
     * @param eid
     * @param sid
     * @param format
     * @return
     */
    @RequestMapping(value = "/img/{eid}/{sid}",method = RequestMethod.POST, consumes = "text/plain")
    JSONObject upLoadImg(@RequestBody String base64,
                         @PathVariable(value = "eid") String eid,
                         @PathVariable(value = "sid") String sid,
                         @RequestParam(value = "format", defaultValue = "png") String format);

    /**
     * 覆盖更改图片,如果没有对应图片则新建上传
     * @param base64
     * @param eid
     * @param sid
     * @param format
     * @return
     */
    @RequestMapping(value = "/img/{eid}/{sid}",method = RequestMethod.PUT, consumes = "text/plain")
    JSONObject updateImg(@RequestBody String base64,
                         @PathVariable(value = "eid") String eid,
                         @PathVariable(value = "sid") String sid,
                         @RequestParam(value = "format", defaultValue = "png") String format);

    /**
     * 删除指定的图片文件，如果文件不存在则返回错误信息
     * @param eid
     * @param sid
     * @param format
     * @return
     */
    @RequestMapping(value = "/img/{eid}/{sid}",method = RequestMethod.DELETE)
    JSONObject deleteImg(@PathVariable(value = "eid") String eid,
                         @PathVariable(value = "sid") String sid,
                         @RequestParam(value = "format", defaultValue = "png") String format);

    @RequestMapping(value = "/getImg/other",method = RequestMethod.POST, consumes = "application/json")
    JSONObject getImgOther(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/uploadImg/other",method = RequestMethod.POST, consumes = "application/json")
    JSONObject uploadImgOther(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/updateImg/other",method = RequestMethod.POST, consumes = "application/json")
    JSONObject updateImgOther(@RequestBody JSONObject jsonObject);

    @RequestMapping(value = "/deleteImg/other",method = RequestMethod.POST, consumes = "application/json")
    JSONObject deleteImgOther(@RequestBody JSONObject jsonObject);

}
