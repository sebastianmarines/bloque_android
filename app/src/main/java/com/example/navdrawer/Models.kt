package com.example.navdrawer

import com.google.gson.annotations.SerializedName

data class TagModel(
    @SerializedName("tagId")
    var id: Int,
    var nombre: String,
    var descripcion: String,
)

data class OSCModel(
    @SerializedName("oscId")
    var id: Int,
    var nombre: String,
    var descripcion: String,
    var direccion: String,
    var telefono: String,
    var email: String,
    var horario: String,
    var tags: List<TagModel>,
    @SerializedName("coordenadas_latitud")
    var latitud: Double,
    @SerializedName("coordenadas_longitud")
    var longitud: Double,
)

data class MapPageModel(
    @SerializedName("oscs")
    var organizaciones: List<OSCModel>,
    var tags: List<TagModel>,
)

data class LoginResponse(
    var token: String,
)

data class UserModel(
    var username: String,
    var email: String,
    var phone: String,
    @SerializedName("first_name")
    var firstName: String,
    @SerializedName("last_name")
    var lastName: String,
)
