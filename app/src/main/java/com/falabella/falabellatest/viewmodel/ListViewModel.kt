package com.falabella.falabellatest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.falabella.falabellatest.model.Indicator
import com.falabella.falabellatest.model.IndicatorsApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel() {

    private val indicatorsApiService  = IndicatorsApiService()
    private val disposable = CompositeDisposable()

    var indicator = MutableLiveData<Indicator>()
    val listLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun refresh(){
        fetchFromRemote()
    }

    private fun fetchFromRemote(){
        loading.value = true
        disposable.add(
            indicatorsApiService.getIndicators()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<Indicator>(){
                    override fun onSuccess(indic: Indicator) {
                        loading.value = false
                        listLoadError.value = false
                        indicator.value = indic
                    }

                    override fun onError(e: Throwable) {
                        listLoadError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}