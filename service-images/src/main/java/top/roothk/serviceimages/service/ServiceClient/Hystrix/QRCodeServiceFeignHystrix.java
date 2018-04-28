package top.roothk.serviceimages.service.ServiceClient.Hystrix;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import top.roothk.serviceimages.service.ServiceClient.QRCodeServiceFeign;

@Component
public class QRCodeServiceFeignHystrix implements FallbackFactory<QRCodeServiceFeign> {
    @Override
    public QRCodeServiceFeign create(Throwable throwable) {
        return null;
    }
}
