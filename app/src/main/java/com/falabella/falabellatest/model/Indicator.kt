package com.falabella.falabellatest.model

import java.io.Serializable

data class Indicator (
    val version : String,
    val autor : String,
    val fecha : String,
    val uf : IndicatorDetail,
    val ivp : IndicatorDetail,
    val dolar : IndicatorDetail,
    val dolar_intercambio : IndicatorDetail,
    val euro : IndicatorDetail,
    val ipc : IndicatorDetail,
    val utm : IndicatorDetail,
    val imacec : IndicatorDetail,
    val tpm : IndicatorDetail,
    val libra_cobre : IndicatorDetail,
    val tasa_desempleo : IndicatorDetail,
    val bitcoin : IndicatorDetail
)

data class IndicatorDetail(
    val codigo : String,
    val nombre : String,
    val unidad_medida : String,
    val fecha : String,
    val valor : Double
) : Serializable
