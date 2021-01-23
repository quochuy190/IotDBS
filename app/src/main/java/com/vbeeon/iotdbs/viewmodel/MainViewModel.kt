package com.vbeeon.iotdbs.viewmodel

import androidx.lifecycle.MutableLiveData
import com.vbeeon.iotdbs.data.model.User
import com.vbeeon.iotdbs.presentation.base.BaseViewModel
import timber.log.Timber


class MainViewModel  : BaseViewModel() {


    val devicesRes : MutableLiveData<List<User>> = MutableLiveData()

    init {
        Timber.e("init")
    }
    override fun onCleared() {
        super.onCleared()
        Timber.e("here")
    }
    private fun handleError(throwable: Throwable) {
        error.value = throwable
    }

}