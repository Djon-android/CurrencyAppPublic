package com.example.currencyapp.api;

import com.example.currencyapp.pojo.ValuteResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ApiService {
    @GET("daily_json.js")
    Observable<ValuteResponse> getValutes();
}
