package cn.linc.tccfinal.sure;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

@Component
public class WebAppInterceptor implements Interceptor {

	@Override
	public Response intercept(Chain chain) throws IOException {
		Request originalRequest = chain.request();
		
		Request updateRequest = originalRequest.newBuilder()
                .build();
		
        return chain.proceed(updateRequest);
	}

}
