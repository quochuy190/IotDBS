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
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext


class LoginViewModel : BaseViewModel() {
    private var parentJob = Job()

    // By default all the coroutines launched in this scope should be using the Main dispatcher
    val apiFloor1 = ApiClient.getClientFloor1()
    val apiFloor2 = ApiClient.getClientFloor2()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    lateinit var repository: RoomRepository
    lateinit var repositorySwitch: SwichRepository
    lateinit var repositorySubSwitch: SubSwichRepository
    lateinit var repositoryScript: ScriptRepository
    lateinit var repositoryUser: UserRepository
    val devicesRes: MutableLiveData<List<RoomEntity>> = MutableLiveData()
    val scriptsRes: MutableLiveData<List<ScriptEntity>> = MutableLiveData()
    val switchRespon: MutableLiveData<List<SwitchDetailEntity>> = MutableLiveData()
    val swRespon: MutableLiveData<List<SwitchEntity>> = MutableLiveData()
    val resCreateGr: MutableLiveData<Boolean> = MutableLiveData()
    val loginRes: MutableLiveData<List<UserEntity>> = MutableLiveData()

    init {
        Timber.e("init")
        val roomDao = IoTDbsDatabase.getInstance(IotDBSApplication.instance)?.roomDao()
        repository = RoomRepository(roomDao!!)
        val switchDao = IoTDbsDatabase.getInstance(IotDBSApplication.instance)?.switchDao()
        repositorySwitch = SwichRepository(switchDao!!)
        val scriptDao = IoTDbsDatabase.getInstance(IotDBSApplication.instance)?.scriptDao()
        repositoryScript = ScriptRepository(scriptDao!!)
        val subSwitchDao = IoTDbsDatabase.getInstance(IotDBSApplication.instance)?.switchDetailDao()
        repositorySubSwitch = SubSwichRepository(subSwitchDao!!)
        val userDao = IoTDbsDatabase.getInstance(IotDBSApplication.instance)?.userDao()
        repositoryUser = UserRepository(userDao!!)
        //repository.insertAll()
    }

    fun exeCreateGroupRemote(mListSWFl1: List<String>, listFL2: List<String>) {
        try {
            val groupFl1 = Group(ConfigNetwork.mGroupNameReportDeviceFloor1, 44, 67, mListSWFl1)
            val mediaType: MediaType = MediaType.parse("application/json;ty=9")!!
            val gson = Gson()
            val responseString = gson.toJson(CreateGroupRequest(groupFl1))
            val body = RequestBody.create(mediaType, responseString)
            apiFloor1.createGroup(
                ConfigNetwork.mIoTServerFloor1, ConfigNetwork.mIoTServerFloor1_2,
                ConfigNetwork.mIoTServerNameFloor1, body
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.postValue(true) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    loading.postValue(false)
                    error.postValue(it)
                }
                .subscribe { t1: CreateGroupRequest?, t2: Throwable? ->
                    loading.postValue(false)
                    resCreateGr.postValue(true)
                }
            val groupFl2 = Group(ConfigNetwork.mGroupNameReportDeviceFloor2, 44, 67, listFL2)
            val responseStringFL2 = gson.toJson(CreateGroupRequest(groupFl2))
            val bodyFL2 = RequestBody.create(mediaType, responseStringFL2)
            apiFloor2.createGroup(
                ConfigNetwork.mIoTServerFloor2, ConfigNetwork.mIoTServerFloor2_2,
                ConfigNetwork.mIoTServerNameFloor2, bodyFL2
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { loading.postValue(true) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    loading.postValue(false)
                    error.postValue(it)
                }
                .subscribe { t1: CreateGroupRequest?, t2: Throwable? ->
                    loading.postValue(false)
                }
        } catch (ex: Exception) {
            loading.postValue(false)
            ex.printStackTrace()
            error.postValue(ex)
        }



    }

    override fun onCleared() {
        super.onCleared()
        Timber.e("here")
    }

    fun insert(room: List<RoomEntity>) = scope.launch(Dispatchers.IO) {
        repository.insertList(room)
    }

    fun insertSwitch(obj: List<SwitchEntity>) = scope.launch(Dispatchers.IO) {
        repositorySwitch.insertList(obj)
    }

    fun insertSubSwitch(list: List<SwitchDetailEntity>) = scope.launch(Dispatchers.IO) {
        repositorySubSwitch.insertList(list)
    }

    fun insertUserAdmin() = scope.launch(Dispatchers.IO) {
        repositoryUser.insert(UserEntity(0, "admin", "admin@2021", "10/01/1990", "",0, 0))
    }
    fun exeLogin(lifecycleOwner: LifecycleOwner, user: String, pass: String) {
        repositoryUser.loadUserAndPass(user, pass).observe(lifecycleOwner, Observer {
            loginRes.postValue(it)
        })
        // resRoom.postValue()
    }
}