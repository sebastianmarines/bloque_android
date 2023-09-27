package com.example.navdrawer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface OSCService {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("orgs/")
    fun getOrganizaciones(): Call<List<OSCModel>>

    @GET("orgs/{id}")
    fun getOrganizacion(@Path("id") id: String): Call<OSCModel>
}
