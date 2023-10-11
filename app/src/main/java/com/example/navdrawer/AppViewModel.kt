package com.example.navdrawer


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppViewModel : ViewModel() {

    private var isLoggedIn = false
    private var token = ""
    private var loginError = ""

    fun setToken(token: String) {
        this.token = token
    }

    fun getToken(): String {
        return token
    }

    fun setLoggedIn() {
        isLoggedIn = true
    }

    fun setLoggedOut() {
        isLoggedIn = false
    }

    fun isUserLoggedIn(): Boolean {
        return isLoggedIn
    }

    fun setLoginError(error: String) {
        loginError = error
    }

    fun getLoginError(): String {
        return loginError
    }

    private fun createRetrofitService(): OSCService {
        val retrofit = Retrofit.Builder().baseUrl("http://ec2-54-81-104-183.compute-1.amazonaws.com:8000/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        Log.e("AppViewModel", "getOrganizaciones: ${retrofit.baseUrl()}")

        return retrofit.create(OSCService::class.java)
    }

    fun getOrganizaciones(osc_state: MutableState<List<OSCModel>>) {
        val service = createRetrofitService()

        val call: Call<List<OSCModel>> = service.getOrganizaciones()

        call.enqueue(object : retrofit2.Callback<List<OSCModel>> {
            override fun onResponse(
                call: Call<List<OSCModel>>, response: retrofit2.Response<List<OSCModel>>
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

    fun getOrganizacion(id: String, osc_state: MutableState<OSCModel>) {
        val service = createRetrofitService()

        val call: Call<OSCModel> = service.getOrganizacion(id)

        call.enqueue(object : retrofit2.Callback<OSCModel> {
            override fun onResponse(
                call: Call<OSCModel>, response: retrofit2.Response<OSCModel>
            ) {
                if (!response.isSuccessful || response.code() != 200) {
                    Log.e("AppViewModel", "onResponse: ${response.code()}")
                    return
                }

                Log.e("AppViewModel", "onResponse: ${response.body()}")
                osc_state.value = response.body()!!
            }

            override fun onFailure(call: Call<OSCModel>, t: Throwable) {
                Log.e("AppViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun loginUser(username: String, password: String) {
        val service = createRetrofitService()

        val login = LoginRequest(username, password)
        val call: Call<LoginResponse> = service.login(login)

        call.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>, response: retrofit2.Response<LoginResponse>
            ) {
                if (!response.isSuccessful || response.code() != 200) {
                    Log.e("AppViewModel", "login onResponse: ${response.code()}")
                    Log.e("AppViewModel", "login onResponse: ${response.body()}")
                    return
                }

                Log.e("AppViewModel", "onResponse: ${response.body()}")
                setToken(response.body()!!.token)
                setLoggedIn()
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("AppViewModel", "onFailure: ${t.message}")
            }
        })
    }
}