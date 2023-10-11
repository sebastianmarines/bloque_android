package com.example.navdrawer

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

class LoginRequest {
    var username: String
    var password: String

    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }
}

interface OSCService {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("orgs/")
    fun getOrganizaciones(): Call<List<OSCModel>>

    @GET("orgs/{id}")
    fun getOrganizacion(@Path("id") id: String): Call<OSCModel>

    @POST("login/")
    fun login(@Body login: LoginRequest): Call<LoginResponse>
}

