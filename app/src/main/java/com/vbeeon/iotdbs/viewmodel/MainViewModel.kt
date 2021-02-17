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
import com.vbeeon.iotdbs.data.model.Switch
import com.vbeeon.iotdbs.data.repository.RoomRepository
import com.vbeeon.iotdbs.data.repository.ScriptRepository
import com.vbeeon.iotdbs.data.repository.SubSwichRepository
import com.vbeeon.iotdbs.data.repository.SwichRepository
import com.vbeeon.iotdbs.presentation.base.BaseViewModel
import com.vbeeon.iotdbs.utils.connvertSwitch
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext


class MainViewModel  : BaseViewModel() {
    private var parentJob = Job()
    // By default all the coroutines launched in this scope should be using the Main dispatcher
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    lateinit var repository: RoomRepository
    lateinit var repositorySwitch: SwichRepository
    lateinit var repositorySubSwitch: SubSwichRepository
    lateinit var repositoryScript: ScriptRepository
    val devicesRes : MutableLiveData<List<RoomEntity>> = MutableLiveData()
    val scriptsRes : MutableLiveData<List<ScriptEntity>> = MutableLiveData()
    val switchRespon : MutableLiveData<List<Switch>> = MutableLiveData()
    val subSwRespon : MutableLiveData<List<SwitchDetailEntity>> = MutableLiveData()
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
    fun loadData( lifecycleOwner: LifecycleOwner) {
        repository.allRooms.observe(lifecycleOwner, Observer {
            devicesRes.postValue(it)
        })
        // resRoom.postValue()
    }
    fun loadDataSwitch( lifecycleOwner: LifecycleOwner, idRoom: Int) {
        repositorySwitch.loadSwitchByRoomId(idRoom).observe(lifecycleOwner, Observer {
            val switchs: MutableList<Switch> = ArrayList()
            for (switch in it){
                repositorySubSwitch.loadSwitchBySwitchId(switch.id).observe(lifecycleOwner, Observer {
                    switchs.add(Switch(switch.id, switch.idRoom, switch.name, switch.isChecked, switch.type, it))
                })
            }
            switchRespon.postValue(switchs)
        })
        // resRoom.postValue()
    }
    fun loadDataSubSwitch( lifecycleOwner: LifecycleOwner, idSwitch: String) {
        repositorySubSwitch.loadSwitchBySwitchId(idSwitch).observe(lifecycleOwner, Observer {
            subSwRespon.postValue(it)
        })
        // resRoom.postValue()
    }
    override fun onCleared() {
        super.onCleared()
        Timber.e("here")
    }
    private fun handleError(throwable: Throwable) {
        error.value = throwable
    }
    fun insert(room: RoomEntity) = scope.launch(Dispatchers.IO) {
        repository.insert(room)
    }
    fun insertSwitch(obj: SwitchEntity) = scope.launch(Dispatchers.IO) {
        repositorySwitch.insert(obj)
    }
    fun insertSubSwitch(list: List<SwitchDetailEntity>) = scope.launch(Dispatchers.IO) {
        repositorySubSwitch.insertList(list)
    }
    fun insertScript(obj: ScriptEntity) = scope.launch(Dispatchers.IO) {
        repositoryScript.insert(obj)
    }
    fun loadDataScript( lifecycleOwner: LifecycleOwner) {
        repositoryScript.loadAllScript().observe(lifecycleOwner, Observer {
            scriptsRes.postValue(it)
        })
        // resRoom.postValue()
    }
}