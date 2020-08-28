package com.falabella.falabellatest.model

import io.reactivex.Single
import retrofit2.http.GET

interface IndicatorsApi {
    @GET("api")
    fun getIndicators() : Single<Indicator>
}