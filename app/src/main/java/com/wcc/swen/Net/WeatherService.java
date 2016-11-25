package com.wcc.swen.net;

import com.wcc.swen.model.WeatherWrapper;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by WangChenchen on 2016/11/25.
 */

public interface WeatherService {

    @GET("v3/weather/daily.json")
    Observable<WeatherWrapper> getWeather(@QueryMap Map<String, String> map);
}
