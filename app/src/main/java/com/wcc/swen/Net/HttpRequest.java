package com.wcc.swen.Net;

import com.wcc.swen.model.WeatherWrapper;
import com.wcc.swen.utils.Url;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by WangChenchen on 2016/11/25.
 */

public class HttpRequest {

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;

    private WeatherService weatherService;

    private Observable.Transformer<WeatherWrapper, WeatherWrapper> schedulersTransformer;

    private HttpRequest(){
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(okHttpClientBuilder.build())
                .baseUrl(Url.weatherUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        weatherService = retrofit.create(WeatherService.class);

        schedulersTransformer = WeatherWrapperObservable -> WeatherWrapperObservable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    private Observable.Transformer<WeatherWrapper, WeatherWrapper> applySchedulers()
    {
        return schedulersTransformer;
    }

    public static class SingletonHolder{
        private static final HttpRequest instance = new HttpRequest(); 
    }
    
    public static HttpRequest getInstance()
    {
        return SingletonHolder.instance;
    }
    
    public void getWeather(Subscriber<WeatherWrapper> subscriber)
    {
        Map<String, String> map = new HashMap<>();
//        ?key=wwl9dtjoxf2ydspl&location=nanjing&language=zh-Hans&unit=c&start=0&days=5

        map.put("key", "wwl9dtjoxf2ydspl");
        map.put("location", "beijing");
        map.put("language", "zh-Hans");
        map.put("unit", "c");
        map.put("start", "0");
        map.put("days", "5");
        weatherService.getWeather(map)
                .compose(applySchedulers())
                .subscribe(subscriber);
    }
}
