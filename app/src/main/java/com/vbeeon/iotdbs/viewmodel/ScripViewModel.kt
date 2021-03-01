package com.vbeeon.iotdbs.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.google.gson.Gson
import com.vbeeon.iotdbs.IotDBSApplication
import com.vbeeon.iotdbs.data.local.IoTDbsDatabase
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.ScriptEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.data.model.CreateGroupRequest
import com.vbeeon.iotdbs.data.model.Group
import com.vbeeon.iotdbs.data.remote.ApiClient
import com.vbeeon.iotdbs.data.repository.RoomRepository
import com.vbeeon.iotdbs.data.repository.ScriptRepository
import com.vbeeon.iotdbs.data.repository.SubSwichRepository
import com.vbeeon.iotdbs.data.repository.SwichRepository
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


class ScripViewModel : BaseViewModel() {
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
    val devicesRes: MutableLiveData<List<RoomEntity>> = MutableLiveData()
    val scriptsRes: MutableLiveData<List<ScriptEntity>> = MutableLiveData()
    val switchRespon: MutableLiveData<List<SwitchDetailEntity>> = MutableLiveData()
    val swRespon: MutableLiveData<List<SwitchEntity>> = MutableLiveData()
    val resCreateGr: MutableLiveData<Boolean> = MutableLiveData()

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
        //repository.insertAll()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.e("here")
    }

    fun insertScript() = scope.launch(Dispatchers.IO) {
        var listAutoScript : MutableList<ScriptEntity> = mutableListOf()
        var mListString : MutableList<String> = mutableListOf()
        listAutoScript.add(ScriptEntity(0, "12h 30 tự động tắt đèn khu làm việc tầng 1", "Tắt đèn tầng 1", true, mListString, 1, 0))
        listAutoScript.add(ScriptEntity(1, "13h 30 tự động bật đèn khu làm việc tầng 1", "Bật đèn tầng 1", true, mListString, 1, 0))
        listAutoScript.add(ScriptEntity(2, "12h 30 tự động tắt đèn khu làm việc tầng 2", "Tắt đèn tầng 2", true, mListString, 1, 0))
        listAutoScript.add(ScriptEntity(3, "12h 30 tự động bật đèn khu làm việc tầng 2", "Bật đèn tầng 2", true, mListString, 1, 0))
        repositoryScript.insertList(listAutoScript)
        SharedPrefs.instance.put(ConstantCommon.KEY_INSERT_AUTO_SCRIPT, true)
    }

    fun loadDataScriptAuto(lifecycleOwner: LifecycleOwner) {
        repositoryScript.loadScriptByRoomId(0).observe(lifecycleOwner, Observer {
            scriptsRes.postValue(it)
        })
        // resRoom.postValue()
    }
    fun loadDataScript(lifecycleOwner: LifecycleOwner) {
        repositoryScript.loadScriptByRoomId(1).observe(lifecycleOwner, Observer {
            scriptsRes.postValue(it)
        })
        // resRoom.postValue()
    }

    fun exeCreateScriptRemoteF2(groupName: String, listSW: List<String>) {
        val groupFl2 = Group(groupName, 44, 67, listSW)

        val mediaType: MediaType = MediaType.parse("application/json;ty=9")!!
        Timber.e(groupFl2.mid[0])
        val gson = Gson()
        val responseString = gson.toJson(CreateGroupRequest(groupFl2))
        val body = RequestBody.create(mediaType, responseString)
        apiFloor2.createGroup(
            ConfigNetwork.mIoTServerFloor2, ConfigNetwork.mIoTServerFloor2_2,
            ConfigNetwork.mIoTServerNameFloor2, body
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            //.doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                loading.postValue(false)
                error.postValue(it)
            }
            .subscribe { t1: CreateGroupRequest?, t2: Throwable? ->
                loading.postValue(false)
            }
    }

    fun exeCreateScriptRemoteF1(groupName: String, listSW: List<String>) {
        val groupF1 = Group(groupName, 44, 67, listSW)
        val mediaType: MediaType = MediaType.parse("application/json;ty=9")!!
        val gson = Gson()
        val responseString = gson.toJson(CreateGroupRequest(groupF1))
        val body = RequestBody.create(mediaType, responseString)
        apiFloor1.createGroup(
            ConfigNetwork.mIoTServerFloor1, ConfigNetwork.mIoTServerFloor1_2,
            ConfigNetwork.mIoTServerNameFloor1, body
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            // .doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                loading.postValue(false)
                error.postValue(it)
            }
            .subscribe { t1: CreateGroupRequest?, t2: Throwable? ->
                loading.postValue(false)
            }
    }
}