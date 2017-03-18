package cn.weiyf.dlframe.net;

import android.webkit.URLUtil;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import cn.weiyf.dlframe.DLApplication;
import cn.weiyf.dlframe.R;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/12/28.
 */

public class DLHttp {

    private static DLHttp sInstance;


    static {
        if (sInstance == null) {
            synchronized (DLHttp.class) {
                if (sInstance == null) {
                    sInstance = new DLHttp();
                }
            }
        }
    }


    public static class Builder {

        private OkHttpClient.Builder mOkHttpClient;

        private Retrofit.Builder mRetrofit;

        private String mBaseUrl;

        private Converter.Factory mFactory;

        private Interceptor mLoggingInterceptor = null;

        private Interceptor mCacheInterceptor = null;

        public Builder() {
            mOkHttpClient = new OkHttpClient.Builder();
            mRetrofit = new Retrofit.Builder();
        }

        public Builder connectTimeout(int connectTimeout) {
            mOkHttpClient.connectTimeout(connectTimeout, TimeUnit.SECONDS);
            return this;
        }

        public Builder writeTimeout(int writeTimeout) {
            mOkHttpClient.writeTimeout(writeTimeout, TimeUnit.SECONDS);
            return this;
        }

        public Builder readTimeout(int readTimeout) {
            mOkHttpClient.readTimeout(readTimeout, TimeUnit.SECONDS);
            return this;
        }

        public Builder retryOnConnectionFailure(boolean retryOnConnectionFailure) {
            mOkHttpClient.retryOnConnectionFailure(retryOnConnectionFailure);
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            mBaseUrl = baseUrl;
            return this;
        }

        public Builder addCallAdapterFactory(CallAdapter.Factory factory) {
            mRetrofit.addCallAdapterFactory(factory);
            return this;
        }

        public Builder addConverterFactory(Converter.Factory factory) {
            mFactory = factory;
            return this;
        }

        public Builder setLoggerInterceptor(Interceptor loggerInterceptor) {
            mLoggingInterceptor = loggerInterceptor;
            return this;
        }

        public Builder setCacheInterceptor(Interceptor cacheInterceptor) {
            mCacheInterceptor = cacheInterceptor;
            return this;
        }

        public Builder cache(int size) {
            if (mCacheInterceptor == null) {
                mCacheInterceptor = new CacheInterceptor();
            }
            File cacheFile = new File(DLApplication.mInstance.getCacheDir(), R.string.app_name + "-cache");
            Cache cache = new Cache(cacheFile, 1024 * 1024 * size);
            mOkHttpClient.cache(cache).addInterceptor(mCacheInterceptor);
            return this;
        }

        public Builder addCommonParameters(HashMap<String, String> parametersMap) {
            CommonParametersInterceptor parametersInterceptor = new CommonParametersInterceptor(parametersMap);
            mOkHttpClient.addInterceptor(parametersInterceptor);
            return this;
        }


        public <S> S create(Class<S> serviceClass) {
            if (URLUtil.isValidUrl(mBaseUrl)) {
                mRetrofit.baseUrl(mBaseUrl);
            } else {
                throw new IllegalArgumentException("please setup the baseUrl");
            }
            if (mFactory != null) {
                mRetrofit.addConverterFactory(mFactory);
            } else {
                mRetrofit.addConverterFactory(GsonConverterFactory.create());
            }
            if (DLApplication.mInstance.isDebug()) {
                if (mLoggingInterceptor != null) {
                    mOkHttpClient.addInterceptor(mLoggingInterceptor);
                } else {
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    mOkHttpClient.addInterceptor(loggingInterceptor);
                }
            }

            return mRetrofit.client(mOkHttpClient.build()).build().create(serviceClass);
        }


    }


}
