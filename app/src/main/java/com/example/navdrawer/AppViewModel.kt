package com.example.navdrawer


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.navigation.NavController
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.MarkerState

class AppViewModel : ViewModel() {

    private var isLoggedIn = false
    private var token = ""
    private var loginError = ""
    private var parentNavController: NavController? = null

    fun setToken(token: String) {
        this.token = token
    }

    fun getToken(): String {
        return token
    }

    fun setLoggedIn(parentNavController: NavController) {
        isLoggedIn = true
        this.parentNavController = parentNavController
    }

    fun setLoggedOut() {
        isLoggedIn = false
        parentNavController?.navigate("LoginPage")
        parentNavController = null
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
        val retrofit = Retrofit.Builder().baseUrl("http://172.21.219.47:8000/api/")
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

    fun getOrganizacion(id: String, osc_state: MutableState<OSCModel>, markerState: MarkerState = MarkerState()) {
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
                markerState.position = LatLng(
                    response.body()!!.latitud,
                    response.body()!!.longitud
                )
            }

            override fun onFailure(call: Call<OSCModel>, t: Throwable) {
                Log.e("AppViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun loginUser(username: String, password: String, nav: NavController) {
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
                    setLoginError("Usuario o contraseña incorrectos")
                    nav.navigate("LoginPage")
                    return
                }

                Log.e("AppViewModel", "onResponse: ${response.body()}")
                setToken(response.body()!!.token)
                setLoginError("")
                setLoggedIn(parentNavController = nav)
                nav.navigate("MainPage")
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("AppViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun registerUser(
        username: String,
        password: String,
        email: String,
        phone: String,
        first_name: String,
        last_name: String,
        nav: NavController
    ) {
        val service = createRetrofitService()

        val register = RegisterRequest(username, password, email, phone, first_name, last_name)
        val call: Call<LoginResponse> = service.register(register)

        call.enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>, response: retrofit2.Response<LoginResponse>
            ) {
                if (!response.isSuccessful || response.code() != 200) {
                    Log.e("AppViewModel", "register onResponse: ${response.code()}")
                    Log.e("AppViewModel", "register onResponse: ${response.body()}")
                    setLoginError("El usuario ya existe")
                    nav.navigate("RegisterPage")
                    return
                }

                Log.e("AppViewModel", "onResponse: ${response.body()}")
                setToken(response.body()!!.token)
                setLoginError("")
                setLoggedIn(parentNavController = nav)
                nav.navigate("MainPage")
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("AppViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getUserProfile(user_state: MutableState<UserModel>) {
        val service = createRetrofitService()

        val call: Call<UserModel> = service.me(token = "Bearer $token")

        call.enqueue(object : retrofit2.Callback<UserModel> {
            override fun onResponse(
                call: Call<UserModel>, response: retrofit2.Response<UserModel>
            ) {
                if (!response.isSuccessful || response.code() != 200) {
                    Log.e("AppViewModel", "onResponse: ${response.code()}")
                    return
                }

                Log.e("AppViewModel", "onResponse: ${response.body()}")
                user_state.value = response.body()!!
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.e("AppViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getMapData(map_state: MutableState<MapPageModel>, organizaciones: MutableState<List<OSCModel>>) {
        val service = createRetrofitService()

        val call: Call<MapPageModel> = service.getMapInfo()

        call.enqueue(object : retrofit2.Callback<MapPageModel> {
            override fun onResponse(
                call: Call<MapPageModel>, response: retrofit2.Response<MapPageModel>
            ) {
                if (!response.isSuccessful || response.code() != 200) {
                    Log.e("AppViewModel", "onResponse: ${response.code()}")
                    return
                }

                Log.e("AppViewModel", "onResponse: ${response.body()}")
                map_state.value = response.body()!!
                organizaciones.value = response.body()!!.organizaciones
            }

            override fun onFailure(call: Call<MapPageModel>, t: Throwable) {
                Log.e("AppViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun editOrg(id: String, latitud: Double, longitud: Double) {
        val service = createRetrofitService()

        val request = EditRequest(latitud, longitud)

        val call: Call<OSCModel> = service.editOrganizacion(id, request)

        call.enqueue(object : retrofit2.Callback<OSCModel> {
            override fun onResponse(
                call: Call<OSCModel>, response: retrofit2.Response<OSCModel>
            ) {
                if (!response.isSuccessful || response.code() != 200) {
                    Log.e("AppViewModel", "onResponse: ${response.code()}")
                    return
                }

                Log.e("AppViewModel", "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<OSCModel>, t: Throwable) {
                Log.e("AppViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getFavoritos(organizaciones: MutableState<List<OSCModel>>) {
        val service = createRetrofitService()

        val call: Call<List<OSCModel>> = service.getFavoritos(token = "Bearer $token")

        call.enqueue(object : retrofit2.Callback<List<OSCModel>> {
            override fun onResponse(
                call: Call<List<OSCModel>>, response: retrofit2.Response<List<OSCModel>>
            ) {
                if (!response.isSuccessful || response.code() != 200) {
                    Log.e("Favoritos", "onResponse: ${response.code()}")
                    return
                }

                Log.e("Favoritos", "onResponse: ${response.body()}")
                organizaciones.value = response.body()!!
            }

            override fun onFailure(call: Call<List<OSCModel>>, t: Throwable) {
                Log.e("Favoritos", "onFailure: ${t.message}")
            }
        })
    }

    fun addFavorito(id: String) {
        val service = createRetrofitService()

        val call: Call<OSCModel> = service.addFavorito(token = "Bearer $token", id = id)

        call.enqueue(object : retrofit2.Callback<OSCModel> {
            override fun onResponse(
                call: Call<OSCModel>, response: retrofit2.Response<OSCModel>
            ) {
                if (!response.isSuccessful || response.code() != 200) {
                    Log.e("AppViewModel", "onResponse: ${response.code()}")
                    return
                }

                Log.e("AppViewModel", "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<OSCModel>, t: Throwable) {
                Log.e("AppViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getTags(tags: MutableState<List<TagModel>>) {
        val service = createRetrofitService()

        val call: Call<List<TagModel>> = service.getTags()

        call.enqueue(object : retrofit2.Callback<List<TagModel>> {
            override fun onResponse(
                call: Call<List<TagModel>>, response: retrofit2.Response<List<TagModel>>
            ) {
                if (!response.isSuccessful || response.code() != 200) {
                    Log.e("AppViewModel", "onResponse: ${response.code()}")
                    return
                }

                Log.e("AppViewModel", "onResponse: ${response.body()}")
                tags.value = response.body()!!
            }

            override fun onFailure(call: Call<List<TagModel>>, t: Throwable) {
                Log.e("AppViewModel", "onFailure: ${t.message}")
            }
        })
    }
}