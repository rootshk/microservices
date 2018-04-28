package top.roothk.serviceimages.service.ServiceClient;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import top.roothk.serviceimages.service.ServiceClient.Hystrix.QRCodeServiceFeignHystrix;

@Component
@FeignClient(value = "service-qrcode" ,fallbackFactory = QRCodeServiceFeignHystrix.class)
public interface QRCodeServiceFeign {

    @RequestMapping(value = "service/qrCode/{uri}", method = RequestMethod.GET)
    String getQRCodeGet(@PathVariable("uri") String uri,
                        @RequestParam(value = "f", defaultValue = "PNG") String f,
                        @RequestParam(value = "w", defaultValue = "300") Integer w,
                        @RequestParam(value = "h", defaultValue = "300") Integer h);

    @RequestMapping(value = "/service/qrCode",method = RequestMethod.GET)
    String getQRCode();

    @RequestMapping(value = "/service/qrCode",method = RequestMethod.POST, consumes = "application/json")
    String getQRCode1(@RequestBody JSONObject jsonObject);
}
