package com.vbeeon.iotdbs.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.vbeeon.iotdbs.IotDBSApplication
import com.vbeeon.iotdbs.data.local.IoTDbsDatabase
import com.vbeeon.iotdbs.data.local.entity.RoomEntity
import com.vbeeon.iotdbs.data.local.entity.ScriptEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchDetailEntity
import com.vbeeon.iotdbs.data.local.entity.SwitchEntity
import com.vbeeon.iotdbs.data.model.*
import com.vbeeon.iotdbs.data.remote.ApiClient
import com.vbeeon.iotdbs.data.repository.RoomRepository
import com.vbeeon.iotdbs.data.repository.ScriptRepository
import com.vbeeon.iotdbs.data.repository.SubSwichRepository
import com.vbeeon.iotdbs.data.repository.SwichRepository
import com.vbeeon.iotdbs.presentation.base.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConfigNetwork
import kotlin.coroutines.CoroutineContext


class SplashViewModel : BaseViewModel() {
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
    val subSwRespon: MutableLiveData<List<SwitchDetailEntity>> = MutableLiveData()

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

    fun exeCreateGroupRemote() {
        var mListSWFl1: MutableList<String> = mutableListOf()
        var mListSWFl2: MutableList<String> = mutableListOf()
        repositorySubSwitch.loadAllSubSwitchFloor(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap({ it ->
                    Timber.e("" + it.size)
                    for (sw in it) {
                        mListSWFl1.add(sw.sortName)
                    }
                    val groupFl1 = Group(ConfigNetwork.mGroupNameReportDeviceFloor1, 0, 0, mListSWFl1)
                    Timber.e(groupFl1.mid[0])
                    return@flatMap apiFloor1.createGroup(CreateGroupRequest(groupFl1), ConfigNetwork.mIoTServerFloor1,
                            ConfigNetwork.mIoTServerNameFloor1, ConfigNetwork.mIoTClientNameFloor1, ConfigNetwork.mGroupNameReportDeviceFloor1)
                })
                .subscribe { t1: ApiResult<LoginSupervisorRemoteEntity>?, t2: Throwable? ->

                }
        repositorySubSwitch.loadAllSubSwitchFloor(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap({ it ->
                    Timber.e("" + it.size)
                    for (sw in it) {
                        mListSWFl2.add(sw.sortName)
                    }
                    val groupFl2 = Group(ConfigNetwork.mGroupNameReportDeviceFloor2, 0, 0, mListSWFl2)
                    Timber.e(groupFl2.mid[0])
                    return@flatMap apiFloor1.createGroup(CreateGroupRequest(groupFl2), ConfigNetwork.mIoTServerFloor2,
                            ConfigNetwork.mIoTServerNameFloor2, ConfigNetwork.mIoTClientNameFloor2, ConfigNetwork.mGroupNameReportDeviceFloor2)
                })
                .subscribe { t1: ApiResult<LoginSupervisorRemoteEntity>?, t2: Throwable? ->

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

    fun insertScript(obj: ScriptEntity) = scope.launch(Dispatchers.IO) {
        repositoryScript.insert(obj)
    }

    fun loadDataScript(lifecycleOwner: LifecycleOwner) {
        repositoryScript.loadAllScript().observe(lifecycleOwner, Observer {
            scriptsRes.postValue(it)
        })
        // resRoom.postValue()
    }
}