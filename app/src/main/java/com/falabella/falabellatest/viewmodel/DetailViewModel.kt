package com.falabella.falabellatest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.falabella.falabellatest.model.IndicatorDetail

class DetailViewModel : ViewModel() {
    val indicatorDetail = MutableLiveData<IndicatorDetail>()

    fun showDetail(detail : IndicatorDetail){
        indicatorDetail.value= detail
    }

}