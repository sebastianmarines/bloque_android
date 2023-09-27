package com.example.navdrawer

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers

public interface OSCService {
    @Headers(
        "Accept: application/json",
        "Content-type: application/json"
    )
    @GET("orgs/")
    fun getOrganizaciones(): Call<List<OSCModel>>
}
