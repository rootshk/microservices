package top.roothk.serviceqrcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServiceQrcodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceQrcodeApplication.class, args);
	}

//	//在 Spring Data Redis 中集成 Fastjson
//	@Bean
//	public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//		RedisTemplate redisTemplate = new RedisTemplate();
//		redisTemplate.setConnectionFactory(redisConnectionFactory);
//
//		GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
//		redisTemplate.setDefaultSerializer(fastJsonRedisSerializer);//设置默认的Serialize，包含 keySerializer & valueSerializer
//
//		//redisTemplate.setKeySerializer(fastJsonRedisSerializer);//单独设置keySerializer
//		//redisTemplate.setValueSerializer(fastJsonRedisSerializer);//单独设置valueSerializer
//		return redisTemplate;
//	}
}
