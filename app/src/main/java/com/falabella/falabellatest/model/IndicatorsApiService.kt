package com.falabella.falabellatest.model

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class IndicatorsApiService {

    private val BASE_URL = "https://www.mindicador.cl"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(IndicatorsApi::class.java)

    fun getIndicators() : Single<Indicator> {
        return  api.getIndicators()
    }

}