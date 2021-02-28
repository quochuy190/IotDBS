package com.vbeeon.iotdbs.data.remote

import com.vbeeon.iotdbs.data.model.*
import io.reactivex.rxjava3.core.Single
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    // https://api.themoviedb.org/3/movie/popular?api_key=6e63c2317fbe963d76c3bdc2b785f6d1&page=1
    // https://api.themoviedb.org/3/movie/299534?api_key=6e63c2317fbe963d76c3bdc2b785f6d1
    // https://api.themoviedb.org/3/


    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id: Int): Single<User>

    @POST("api/v1/subscriber/login")
    fun login(@Body loginRequest: LoginRequest): Single<ApiResult<LoginEntity>>

    @POST("api/v1/supervisor/login")
    fun loginSupervisor(@Body request: LoginSuperVisorRequest): Single<ApiResult<LoginSupervisorRemoteEntity>>

    @POST("{iotserver_id}/{iotserver_id_2}/{iotserver_name}")
    fun createGroup(@Path("iotserver_id") iotserver_id: String,
                    @Path("iotserver_id_2") iotserver_id_2: String,
                    @Path("iotserver_name") iotserver_name: String,
                    @Body request: RequestBody
    ): Single<CreateGroupRequest>

    @GET("{iotserver_id}/{iotserver_name}/{iotclient_name}/{group_name}/fopt")
    fun getStateGroup(@Path("iotserver_id") iotserver_id: String,
                      @Path("iotserver_name") iotserver_name: String,
                      @Path("iotclient_name") iotclient_name: String,
                      @Path("group_name") id: String): Call<ResponseBody>

    @POST("{iotserver_id}/{iotserver_name}/{iotclient_name}/{group_name}/fopt")
    fun controlGroup(@Path("iotserver_id") iotserver_id: String,
                     @Path("iotserver_name") iotserver_name: String,
                     @Path("iotclient_name") iotclient_name: String,
                     @Path("group_name") id: String,
                     @Body request: RequestBody
    ): Single<ResponGetStateGroup>

    @POST("{iotserver_id}/{iotserver_name}/{iotclient_name}/{linkControl}")
    fun controlSubGroup(@Path("iotserver_id") iotserver_id: String,
                     @Path("iotserver_name") iotserver_name: String,
                     @Path("iotclient_name") iotclient_name: String,
                     @Path("linkControl", encoded=true) id: String,
                     @Body request: RequestBody
    ): Single<ResponGetStateGroup>
}