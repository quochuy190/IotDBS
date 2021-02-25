package com.vbeeon.iotdbs.data.remote

import com.vbeeon.iotdbs.data.model.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

    @POST("{iotserver_id}/{iotserver_name}/{iotclient_name}/{group_name}")
    fun createGroup(@Body request: CreateGroupRequest,
                    @Path("iotserver_id") iotserver_id: String,
                    @Path("iotserver_name") iotserver_name: String,
                    @Path("iotclient_name") iotclient_name: String,
                    @Path("group_name") id: String): Single<ApiResult<LoginSupervisorRemoteEntity>>

    @POST("{iotserver_id}/{iotserver_name}/{iotclient_name}/{group_name}/fopt")
    fun getStateGroup(@Path("iotserver_id") iotserver_id: String,
                      @Path("iotserver_name") iotserver_name: String,
                      @Path("iotclient_name") iotclient_name: String,
                      @Path("group_name") id: String): Single<ResponGetStateGroup>
}