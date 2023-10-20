package com.example.navdrawer

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

open class LoginRequest {
    var username: String
    var password: String

    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }
}

class RegisterRequest : LoginRequest {
    var email: String
    var phone: String
    var first_name: String
    var last_name: String

    constructor(
        username: String,
        password: String,
        email: String,
        phone: String,
        first_name: String,
        last_name: String
    ) : super(username, password) {
        this.email = email
        this.phone = phone
        this.first_name = first_name
        this.last_name = last_name
    }
}

interface OSCService {
    @Headers(
        "Accept: application/json", "Content-type: application/json"
    )
    @GET("orgs/")
    fun getOrganizaciones(): Call<List<OSCModel>>

    @GET("orgs/{id}")
    fun getOrganizacion(@Path("id") id: String): Call<OSCModel>

    @POST("orgs/{id}/edit")
    fun editOrganizacion(@Path("id") id: String, @Body organizacion: EditRequest): Call<OSCModel>

    @POST("login/")
    fun login(@Body login: LoginRequest): Call<LoginResponse>

    @POST("register/")
    fun register(@Body register: RegisterRequest): Call<LoginResponse>

    @GET("users/me/")
    fun me(@Header("Authorization") token: String): Call<UserModel>

    @GET("getMapInfo/")
    fun getMapInfo(): Call<MapPageModel>

    @GET("users/me/favorites")
    fun getFavoritos(@Header("Authorization") token: String): Call<List<OSCModel>>

    @POST("users/me/favorites/{id}/")
    fun addFavorito(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Call<OSCModel>
}

