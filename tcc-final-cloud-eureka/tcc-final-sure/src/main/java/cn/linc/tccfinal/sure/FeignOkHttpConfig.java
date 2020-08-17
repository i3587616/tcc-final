package cn.linc.tccfinal.sure;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Feign;

@Configuration
@ConditionalOnClass(Feign.class)
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignOkHttpConfig {

	@Autowired
	Interceptor appInterceptor;
	
	@Bean
	public OkHttpClient okHttpClient() {
		OkHttpClient client=new OkHttpClient.Builder().readTimeout(60, TimeUnit.SECONDS)
				.connectTimeout(60, TimeUnit.SECONDS).writeTimeout(120, TimeUnit.SECONDS)
				.connectionPool(new ConnectionPool(5,5*60*1000, TimeUnit.SECONDS)).addInterceptor(appInterceptor)
				.build();
	    return client;
	}
	
}
