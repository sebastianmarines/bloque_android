package com.example.navdrawer


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppViewModel : ViewModel() {

    private var isLoggedIn = false

    fun setLoggedIn() {
        isLoggedIn = true
    }

    fun setLoggedOut() {
        isLoggedIn = false
    }

    fun isUserLoggedIn(): Boolean {
        return isLoggedIn
    }

    fun getOrganizaciones(osc_state: MutableState<List<OSCModel>>) {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.21.219.47:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        Log.e("AppViewModel", "getOrganizaciones: ${retrofit.baseUrl()}")

        val service = retrofit.create(OSCService::class.java)

        val call: Call<List<OSCModel>> = service.getOrganizaciones()


        call!!.enqueue(object : retrofit2.Callback<List<OSCModel>> {
            override fun onResponse(
                call: Call<List<OSCModel>>,
                response: retrofit2.Response<List<OSCModel>>
            ) {
                if (!response.isSuccessful || response.code() != 200) {
                    Log.e("AppViewModel", "onResponse: ${response.code()}")
                    return
                }

                Log.e("AppViewModel", "onResponse: ${response.body()}")
                osc_state.value = response.body()!!
            }

            override fun onFailure(call: Call<List<OSCModel>>, t: Throwable) {
                Log.e("AppViewModel", "onFailure: ${t.message}")
            }
        })

    }
}