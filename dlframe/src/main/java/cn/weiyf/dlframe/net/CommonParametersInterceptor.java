package cn.weiyf.dlframe.net;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonParametersInterceptor implements Interceptor {

    private final HashMap<String, String> parametersMap;

    CommonParametersInterceptor(HashMap<String, String> parametersMap) {
        this.parametersMap = parametersMap;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();

        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host());

        String[] parameters = new String[parametersMap.size()];
        parameters = parametersMap.keySet().toArray(parameters);


        for (String parameter : parameters) {
            authorizedUrlBuilder
                    .addQueryParameter(parameter, parametersMap.get(parameter));
        }
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }
}