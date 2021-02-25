package com.vbeeon.iotdbs.presentation.base

import androidx.annotation.IntDef
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable

@IntDef(
    BaseViewModel.RQ_START,
    BaseViewModel.RQ_FINISH
)
annotation class Status

abstract class BaseViewModel : ViewModel() {
    companion object {
        const val RQ_START = 0x00
        const val RQ_FINISH = 0x01
    }

    protected val disposables: CompositeDisposable = CompositeDisposable()

    val loading = MutableLiveData<@Status Boolean>()

    val error = MutableLiveData<Throwable>()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}