package com.example.navdrawer.screens.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.UserModel

@Composable
fun ProfilePage(navController: NavController ,viewModel: AppViewModel) {
    var fetched by remember { mutableStateOf(false) }
    var perfil: MutableState<UserModel> = remember {
        mutableStateOf(
            UserModel(
                "",
                "",
                "",
                "",
                "",
            )
        )
    }

    LaunchedEffect(null) {
        if (!fetched) {
            viewModel.getUserProfile(perfil)
        }
    }

    Column {
        Text("Nombre: ${perfil.value.firstName}")
        Text("Apellido: ${perfil.value.lastName}")
        Text("Email: ${perfil.value.email}")
        Text("Username: ${perfil.value.username}")
        Text("Phone: ${perfil.value.phone}")

        Button(onClick = {
            Log.e("ProfilePage", "Log Out")
            viewModel.setLoginError("")
            viewModel.setToken("")
            viewModel.setLoggedOut()
            navController.navigate("LoginPage")
        }) {
            Text("Log Out")
        }
    }
}