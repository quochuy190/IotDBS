package com.vbeeon.iotdbs.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.vbeeon.iotdbs.IotDBSApplication
import com.vbeeon.iotdbs.data.local.IoTDbsDatabase
import com.vbeeon.iotdbs.data.local.entity.*
import com.vbeeon.iotdbs.data.model.CreateGroupRequest
import com.vbeeon.iotdbs.data.model.Group
import com.vbeeon.iotdbs.data.remote.ApiClient
import com.vbeeon.iotdbs.data.repository.*
import com.vbeeon.iotdbs.presentation.base.BaseViewModel
import com.vbeeon.iotdbs.utils.SharedPrefs
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.adapter.rxjava3.Result.response
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConfigNetwork
import vn.neo.smsvietlott.common.di.util.ConstantCommon
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext


class UserViewModel : BaseViewModel() {
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    lateinit var repository: RoomRepository
    lateinit var repositoryUser: UserRepository
    val loadRoomRes: MutableLiveData<List<RoomEntity>> = MutableLiveData()

    init {
        Timber.e("init")
        val roomDao = IoTDbsDatabase.getInstance(IotDBSApplication.instance)?.roomDao()
        repository = RoomRepository(roomDao!!)
        val userchDao = IoTDbsDatabase.getInstance(IotDBSApplication.instance)?.userDao()
        repositoryUser = UserRepository(userchDao!!)
        //repository.insertAll()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.e("here")
    }

    fun loadAllData(lifecycleOwner: LifecycleOwner) {
        repository.allRooms.observe(lifecycleOwner, Observer {
            loadRoomRes.postValue(it)
        })
        // resRoom.postValue()
    }

    fun insertUserAdmin(user : UserEntity) = scope.launch(Dispatchers.IO) {
        repositoryUser.insert(user)
    }
}