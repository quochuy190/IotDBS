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
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import vn.neo.smsvietlott.common.di.util.ConfigNetwork
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext


class MainViewModel : BaseViewModel() {
    private var parentJob = Job()
    val apiFloor1 = ApiClient.getClientFloor1()
    val apiFloor2 = ApiClient.getClientFloor2()

    // By default all the coroutines launched in this scope should be using the Main dispatcher
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    var repository: RoomRepository
    var repositorySwitch: SwichRepository
    var repositorySubSwitch: SubSwichRepository
    var repositoryScript: ScriptRepository
    val devicesRes: MutableLiveData<List<RoomEntity>> = MutableLiveData()
    val scriptsRes: MutableLiveData<List<ScriptEntity>> = MutableLiveData()
    val switchRespon: MutableLiveData<List<SwitchDetailEntity>> = MutableLiveData()
    val swRespon: MutableLiveData<List<SwitchEntity>> = MutableLiveData()
    val subSwRespon: MutableLiveData<List<SwitchDetailEntity>> = MutableLiveData()
    val resControlSubSW: MutableLiveData<Int> = MutableLiveData()
    val resGetStateMain: MutableLiveData<Boolean> = MutableLiveData()
    val resControlAll: MutableLiveData<Boolean> = MutableLiveData()
    val resCreateScript: MutableLiveData<Boolean> = MutableLiveData()

    init {
        val roomDao = IoTDbsDatabase.getInstance(IotDBSApplication.instance)?.roomDao()
        repository = RoomRepository(roomDao!!)
        val switchDao = IoTDbsDatabase.getInstance(IotDBSApplication.instance)?.switchDao()
        repositorySwitch = SwichRepository(switchDao!!)
        val scriptDao = IoTDbsDatabase.getInstance(IotDBSApplication.instance)?.scriptDao()
        repositoryScript = ScriptRepository(scriptDao!!)
        val subSwitchDao = IoTDbsDatabase.getInstance(IotDBSApplication.instance)?.switchDetailDao()
        repositorySubSwitch = SubSwichRepository(subSwitchDao!!)
    }

    fun loadData(lifecycleOwner: LifecycleOwner, floor: Int) {
        repository.loadRoomByFloor(floor).observe(lifecycleOwner, Observer {
            devicesRes.postValue(it)
        })
    }

    fun loadAllData(lifecycleOwner: LifecycleOwner) {
        repository.allRooms.observe(lifecycleOwner, Observer {
            devicesRes.postValue(it)
        })
        // resRoom.postValue()
    }

    fun exe(lifecycleOwner: LifecycleOwner, pass: String) {
        val request = LoginSuperVisorRequest();
        Timber.e("call loginRemote")
        request.password = "fd6aa3889d2415bcbc13803f303f1137"
        request.uuid = "e92947d6-2dcf-3375-b3c8-7789f969de6a"

        val requestLogin = LoginRequest();
        Timber.e("call loginRemote")
        requestLogin.password = "fd6aa3889d2415bcbc13803f303f1137"
        requestLogin.username = "e92947d6-2dcf-3375-b3c8-7789f969de6a"
        apiFloor1.loginSupervisor(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap({ it -> apiFloor2.login(requestLogin) })
            .subscribe { t1: ApiResult<LoginEntity>?, t2: Throwable? ->
                loading.postValue(false)
            }
//                .subscribe { t1: ApiResult<LoginSupervisorRemoteEntity>?, t2: Throwable? ->
//                    Timber.e(t1!!.errorMessage)
//                }

    }

    fun exeGetStateFromRemote1() {
        repositorySubSwitch.loadAllSubSwitchFloor(1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(true) }
            .subscribe { t1, t2 ->
                val call: Call<ResponseBody> = apiFloor1.getStateGroup(
                    ConfigNetwork.mIoTServerFloor1, ConfigNetwork.mIoTServerFloor1_2,
                    ConfigNetwork.mIoTServerNameFloor1,
                    ConfigNetwork.mGroupNameReportDeviceFloor1
                )
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        loading.postValue(false)
                        error.postValue(t)
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>?,
                        response: Response<ResponseBody>?
                    ) {
                        val json = response!!.body()!!.string();
                        val gson = Gson()
                        Timber.e("" + json)
                        val json2: String = json.replace("m2m:cin", "m2m_cin")
                        var testModel = gson.fromJson(json2, ResponGetStateGroup::class.java)
                        Timber.e("" + testModel)
                        for (i in 0..(testModel!!.responsePrimitive.size - 1)) {
                            if (i <= t1.size) {
                                if (testModel!!.responsePrimitive[i].content.m2m.con.sw_report == 1)
                                    t1[i].isChecked = true
                                else
                                    t1[i].isChecked = false
                            }
                        }
                        updateListSubSW(t1)
                        exeGetStateFromRemote2()
                    }
                })

            }
    }

    fun exeGetStateFromRemote2() {
        repositorySubSwitch.loadAllSubSwitchFloor(2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { t1, t2 ->
                val call: Call<ResponseBody> = apiFloor2.getStateGroup(
                    ConfigNetwork.mIoTServerFloor2, ConfigNetwork.mIoTServerFloor2_2,
                    ConfigNetwork.mIoTServerNameFloor2,
                    ConfigNetwork.mGroupNameReportDeviceFloor2
                )
                call.enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        loading.postValue(false)
                        error.postValue(t)
                    }

                    override fun onResponse(
                        call: Call<ResponseBody>?,
                        response: Response<ResponseBody>?
                    ) {
                        loading.postValue(false)
                        val json = response!!.body()!!.string();
                        val gson = Gson()
                        Timber.e("" + json)
                        val json2: String = json.replace("m2m:cin", "m2m_cin")
                        var testModel = gson.fromJson(json2, ResponGetStateGroup::class.java)
                        Timber.e("" + testModel)
                        for (i in 0..(testModel!!.responsePrimitive.size - 1)) {
                            if (i <= t1.size) {
                                if (testModel!!.responsePrimitive[i].content.m2m.con.sw_report == 1)
                                    t1[i].isChecked = true
                                else
                                    t1[i].isChecked = false
                            }
                        }
                        updateListSubSW(t1)
                        resGetStateMain.postValue(true)
                    }
                })

            }

    }

    //    fun exeCreateGroupRemoteBySW(switchid: String) {
//        var mListSWFl1: MutableList<String> = mutableListOf()
//        var mListSWFl2: MutableList<String> = mutableListOf()
//        repositorySubSwitch.loadSubSwitchWithSwId(switchid)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .flatMap({ it ->
//                    if (it[0].floor == 1) {
//                        Timber.e("" + it.size)
//                        for (sw in it) {
//                            mListSWFl1.add(sw.sortName)
//                        }
//                        val groupFl1 = Group(ConfigNetwork.mGroupNameReportDeviceFloor1, 44, 67, mListSWFl1)
//
//                        return@flatMap apiFloor1.createGroup(ConfigNetwork.mIoTServerFloor1,ConfigNetwork.mIoTServerFloor1_2,
//                                ConfigNetwork.mIoTServerNameFloor1, CreateGroupRequest(groupFl1))
//                    } else {
//                        Timber.e("" + it.size)
//                        for (sw in it) {
//                            mListSWFl2.add(sw.sortName)
//                        }
//                        val groupFl2 = Group(ConfigNetwork.mGroupNameReportDeviceFloor2, 44, 67, mListSWFl2)
//
//                        return@flatMap apiFloor2.createGroup( ConfigNetwork.mIoTServerFloor2,ConfigNetwork.mIoTServerFloor2_2,
//                                ConfigNetwork.mIoTServerNameFloor2, CreateGroupRequest(groupFl2))
//                    }
//
//                })
//                .subscribe { t1: CreateGroupRequest, t2: Throwable? ->
//
//                }
//    }
//
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
            .doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                loading.postValue(false)
                error.postValue(it)
            }
            .subscribe { t1: CreateGroupRequest?, t2: Throwable? ->
                loading.postValue(false)
//                resCreateScript.postValue(true)
//                insertScript(ScriptEntity(groupName.toLong(),
//                    "12h 30 tự động tắt đèn khu làm việc tầng 1", "Tắt đèn tầng 1", true, listSW, 1, 1))
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
            .doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                loading.postValue(false)
                error.postValue(it)
            }
            .subscribe { t1: CreateGroupRequest?, t2: Throwable? ->
                loading.postValue(false)
//                resCreateScript.postValue(true)
//                insertScript(ScriptEntity(groupName.toLong(),
//                    "12h 30 tự động tắt đèn khu làm việc tầng 1", "Tắt đèn tầng 1", true, listSW, 1, 1))
                //Đưa lên view
            }
    }

    fun exeControlGroup1(state: Int, nameGroup: String) {
        val controlValue = ControlSubSw(state)
        val controlSW = ControlSwObj("application/json", controlValue)
        val request = RequsetControlSubSw(controlSW)
        val mediaType: MediaType = MediaType.parse("application/json;ty=9")!!
        val gson = Gson()
        val responseString = gson.toJson(request)
        val body = RequestBody.create(mediaType, responseString)
        apiFloor1.controlGroup(
            ConfigNetwork.mIoTServerFloor1, ConfigNetwork.mIoTServerFloor1_2,
            ConfigNetwork.mIoTServerNameFloor1, nameGroup, body
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                loading.postValue(false)
                error.postValue(it)
            }
            .subscribe { t1: ResponGetStateGroup?, t2: Throwable? ->
                try {
                    if (state == 1)
                        resControlAll.postValue(true)
                    else
                        resControlAll.postValue(false)
                    loading.postValue(false)
                } catch (ex: Exception) {
                    loading.postValue(false)
                    ex.printStackTrace()
                    error.postValue(ex)
                }

            }
    }

    fun exeControlGroup2(state: Int, nameGroup: String) {
        val controlValue = ControlSubSw(state)
        val controlSW = ControlSwObj("application/json", controlValue)
        val request = RequsetControlSubSw(controlSW)
        val mediaType: MediaType = MediaType.parse("application/json;ty=9")!!
        val gson = Gson()
        val responseString = gson.toJson(request)
        val body = RequestBody.create(mediaType, responseString)
        apiFloor2.controlGroup(
            ConfigNetwork.mIoTServerFloor2, ConfigNetwork.mIoTServerFloor2_2,
            ConfigNetwork.mIoTServerNameFloor2, nameGroup, body
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                loading.postValue(false)
                error.postValue(it)
            }
            .subscribe { t1: ResponGetStateGroup?, t2: Throwable? ->
                try {
                    if (state == 1)
                        resControlAll.postValue(true)
                    else
                        resControlAll.postValue(false)
                    loading.postValue(false)
                } catch (ex: Exception) {
                    loading.postValue(false)
                    ex.printStackTrace()
                    error.postValue(ex)
                }

            }
    }

    fun exeControlSubSW1(state: Int, linkSubSW: String) {
        val controlValue = ControlSubSw(state)
        val controlSW = ControlSwObj("application/json", controlValue)
        val request = RequsetControlSubSw(controlSW)
        val mediaType: MediaType = MediaType.parse("application/json;ty=4")!!
        val gson = Gson()
        val responseString = gson.toJson(request)
        val body = RequestBody.create(mediaType, responseString)
        apiFloor1.controlSubGroup(
            ConfigNetwork.mIoTServerFloor1, ConfigNetwork.mIoTServerFloor1_2,
            ConfigNetwork.mIoTServerNameFloor1, linkSubSW, body
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                loading.postValue(false)
                error.postValue(it)
            }
            .subscribe { t1: ResponGetStateGroup?, t2: Throwable? ->
                try {
                    loading.postValue(false)
                    resControlSubSW.postValue(state)
                } catch (ex: Exception) {
                    loading.postValue(false)
                    ex.printStackTrace()
                    error.postValue(ex)
                }

            }
    }

    fun exeControlSubSW2(state: Int, linkSubSW: String) {
        val controlValue = ControlSubSw(state)
        val controlSW = ControlSwObj("application/json", controlValue)
        val request = RequsetControlSubSw(controlSW)
        val mediaType: MediaType = MediaType.parse("application/json;ty=4")!!
        val gson = Gson()
        val responseString = gson.toJson(request)
        val body = RequestBody.create(mediaType, responseString)
        apiFloor2.controlSubGroup(
            ConfigNetwork.mIoTServerFloor2, ConfigNetwork.mIoTServerFloor2_2,
            ConfigNetwork.mIoTServerNameFloor2, linkSubSW, body
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                loading.postValue(false)
                error.postValue(it)
            }
            .subscribe { t1: ResponGetStateGroup?, t2: Throwable? ->
                try {
                    loading.postValue(false)
                    resControlSubSW.postValue(state)
                } catch (ex: Exception) {
                    loading.postValue(false)
                    ex.printStackTrace()
                    error.postValue(ex)
                }

            }
    }

    fun loadDataSwitch(lifecycleOwner: LifecycleOwner, idRoom: Int) {
        repositorySwitch.loadSwitchByRoomId(idRoom).observe(lifecycleOwner, Observer {
            swRespon.postValue(it)
        })
    }

    fun loadAllDataSwitch(lifecycleOwner: LifecycleOwner) {
        repositorySwitch.loadAllSwitch().observe(lifecycleOwner, Observer {
            swRespon.postValue(it)
        })
    }

    fun loadAllDataSwitchbyFloor(lifecycleOwner: LifecycleOwner, floor: Int) {
        repositorySwitch.loadAllSwitchByFloor(floor).observe(lifecycleOwner, Observer {
            swRespon.postValue(it)
        })
    }

    fun loadAllSubSwitch(lifecycleOwner: LifecycleOwner) {
        repositorySubSwitch.loadAllSubSwitch().observe(lifecycleOwner, Observer {
            switchRespon.postValue(it)
        })
    }

    fun loadSubSwitchBySwitchId(lifecycleOwner: LifecycleOwner, ids: List<String>) {
        val listSW: MutableList<Switch> = ArrayList()
        repositorySubSwitch.loadSwitchBySwitchIdList(ids).observe(lifecycleOwner, Observer {
            switchRespon.postValue(it)

        })
    }

    fun loadDataSubSwitch(lifecycleOwner: LifecycleOwner, idSwitch: String) {
        repositorySubSwitch.loadSwitchBySwitchId(idSwitch)
            .observe(lifecycleOwner, Observer {
                subSwRespon.postValue(it)
            })
    }

    override fun onCleared() {
        super.onCleared()
        Timber.e("here")
    }

    private fun handleError(throwable: Throwable) {
        error.value = throwable
        loading.postValue(false)
        Timber.e("here" + error.value)
    }

    fun insert(room: List<RoomEntity>) = scope.launch(Dispatchers.IO) {
        repository.insertList(room)
    }


    fun updateNamePantry() = scope.launch(Dispatchers.IO) {
        val mListRoom: MutableList<RoomEntity> = ArrayList()
        val mListSW: MutableList<SwitchEntity> = ArrayList()
        mListRoom.add(RoomEntity(6, "Phòng Pantry", 1, false))
        mListRoom.add(RoomEntity(10, "Phòng Pantry", 2, false))
        mListSW.add(SwitchEntity("SW00578", 10, "P.Pantry T2", true, 2, 2, "Phòng Pantry"))
        mListSW.add(SwitchEntity("SW00582", 6, "P.Pantry", true, 2, 1, "Phòng Pantry"))
        repository.updateList(mListRoom)
        repositorySwitch.updateList(mListSW)
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

    fun updateListSubSW(list: List<SwitchDetailEntity>) = scope.launch(Dispatchers.IO) {
        repositorySubSwitch.updateList(list)
    }

    fun loadDataScript(lifecycleOwner: LifecycleOwner) {
        repositoryScript.loadAllScript().observe(lifecycleOwner, Observer {
            scriptsRes.postValue(it)
        })
        // resRoom.postValue()
    }

    val resCreateGr: MutableLiveData<Boolean> = MutableLiveData()
    fun exeCreateGroupRemote(lisFl1: String, lisFl2: String) {
        try {
            var mListSWFl1: MutableList<String> = mutableListOf()
            var mListSWFl2: MutableList<String> = mutableListOf()
            repositorySubSwitch.loadAllSubSwitchFloor(1)
                .flatMap({ it ->
                    Timber.e("" + it.size)
                    for (sw in it) {
                        mListSWFl1.add(
                            "/" + ConfigNetwork.mIoTServerFloor1 +
                                    "/" + ConfigNetwork.mIoTServerFloor1 +
                                    "/" + ConfigNetwork.mIoTServerNameFloor1 +
                                    "/" + sw.idSwitch +
                                    "/" + sw.sortName + "/report/la"
                        )
                    }
                    val groupFl1 =
                        Group(ConfigNetwork.mGroupNameReportDeviceFloor1, 44, 67, mListSWFl1)
                    val mediaType: MediaType = MediaType.parse("application/json;ty=9")!!
                    val gson = Gson()
                    val responseString = gson.toJson(CreateGroupRequest(groupFl1))
                    val body = RequestBody.create(mediaType, responseString)
                    return@flatMap apiFloor1.createGroup(
                        ConfigNetwork.mIoTServerFloor1, ConfigNetwork.mIoTServerFloor1_2,
                        ConfigNetwork.mIoTServerNameFloor1, body
                    )
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    loading.postValue(false)
                    error.postValue(it)
                }
                .timeout(300, TimeUnit.SECONDS)
                .subscribe { t1: CreateGroupRequest, t2: Throwable? ->
                    repositorySubSwitch.loadAllSubSwitchFloor(2)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap({ it ->
                            Timber.e("" + it.size)
                            for (sw in it) {
                                mListSWFl2.add(
                                    "/" + ConfigNetwork.mIoTServerFloor2 +
                                            "/" + ConfigNetwork.mIoTServerFloor2 +
                                            "/" + ConfigNetwork.mIoTServerNameFloor2 +
                                            "/" + sw.idSwitch +
                                            "/" + sw.sortName + "/report/la"
                                )
                            }
                            val groupFl2 = Group(
                                ConfigNetwork.mGroupNameReportDeviceFloor2,
                                44,
                                67,
                                mListSWFl2
                            )

                            val mediaType: MediaType = MediaType.parse("application/json;ty=9")!!
                            Timber.e(groupFl2.mid[0])
                            val gson = Gson()
                            val responseString = gson.toJson(CreateGroupRequest(groupFl2))
                            val body = RequestBody.create(mediaType, responseString)
                            return@flatMap apiFloor2.createGroup(
                                ConfigNetwork.mIoTServerFloor2, ConfigNetwork.mIoTServerFloor2_2,
                                ConfigNetwork.mIoTServerNameFloor2, body
                            )
                        })
                        .subscribe { t1: CreateGroupRequest, t2: Throwable? ->
                            try {
                                loading.postValue(false)
                                resCreateGr.postValue(true)
                            } catch (ex: Exception) {
                                loading.postValue(false)
                                ex.printStackTrace()
                                error.postValue(ex)
                            }
                        }
                }
        } catch (ex: Exception) {
            loading.postValue(false)
            ex.printStackTrace()
            error.postValue(ex)
        }
        var mListSWFl2: MutableList<String> = mutableListOf()
    }

    fun exeControlSwDimming(state: Int, linkSubSW: String, sw_dim : Int, sw_color: Int) {
        val controlValue = ControlSubSwDimming(state, sw_dim, sw_color)
        val controlSW = ControlSwObjDimming("application/json", controlValue)
        val request = RequsetControlSwDimming(controlSW)
        val mediaType: MediaType = MediaType.parse("application/json;ty=4")!!
        val gson = Gson()
        val responseString = gson.toJson(request)
        val body = RequestBody.create(mediaType, responseString)
        apiFloor2.controlSubGroup(
            ConfigNetwork.mIoTServerFloor2, ConfigNetwork.mIoTServerFloor2_2,
            ConfigNetwork.mIoTServerNameFloor2, linkSubSW, body
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading.postValue(true) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError {
                loading.postValue(false)
                error.postValue(it)
            }
            .subscribe { t1: ResponGetStateGroup?, t2: Throwable? ->
                try {
                    loading.postValue(false)
                    resControlSubSW.postValue(state)
                } catch (ex: Exception) {
                    loading.postValue(false)
                    ex.printStackTrace()
                    error.postValue(ex)
                }

            }
    }
}