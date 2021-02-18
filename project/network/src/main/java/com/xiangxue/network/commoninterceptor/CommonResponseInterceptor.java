package com.xiangxue.network.commoninterceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonResponseInterceptor implements Interceptor {
    private static final String TAG = "json";
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        String method=request.method();
        StringBuilder sb = new StringBuilder();
        if("POST".equals(method)){

            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                }
                sb.delete(sb.length() - 1, sb.length());
            }
        }
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Headers headers = response.headers();


        Log.e(TAG,"json-----请求-url----"+request.url()+"======="+"| canshu:{"+sb.toString()+"}"+"++++++++++"+content);
        return  response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
