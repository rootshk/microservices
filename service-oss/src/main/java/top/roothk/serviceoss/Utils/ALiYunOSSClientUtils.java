package top.roothk.serviceoss.Utils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ALiYunOSSClientUtils {

    //OSS地址
    @Value("${oss.endpoint}")
    private String endpoint;
    //密钥
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    //密钥
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    //命名空间
    @Value("${oss.bucketName}")
    private String bucketName;
    //文件名
    @Value("${oss.key}")
    private String key;
    //
    @Value("${oss.securityToken}")
    private String securityToken;

    /**
     * 返回一个初始化的OSSClient
     */
    public OSSClient getOSSClient(){
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 返回一个配置后的OSSClient
     * @param conf
     */
    public OSSClient getOSSClientConf(ClientConfiguration conf){ return new OSSClient(endpoint, accessKeyId, accessKeySecret, conf); }
}
