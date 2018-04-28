package top.roothk.serviceimages.service.ServiceClient.Hystrix;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import top.roothk.serviceimages.service.ServiceClient.OSSServiceFeign;

@Component
public class OSSServiceFeignHystrix implements FallbackFactory<OSSServiceFeign> {
    @Override
    public OSSServiceFeign create(Throwable throwable) {
        return null;
    }
}
