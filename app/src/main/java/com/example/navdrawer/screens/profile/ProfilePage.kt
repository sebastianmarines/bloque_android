package com.example.navdrawer.screens.profile

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.R
import com.example.navdrawer.UserModel


@Composable
fun ProfilePage(navController: NavController, viewModel: AppViewModel) {
    var fetched by remember { mutableStateOf(false) }

    var perfil: MutableState<UserModel> = remember {
        mutableStateOf(
            UserModel(
                "John",
                "Doe",
                "johndoe@example.com",
                "johndoe",
                "123-456-7890",
            )
        )
    }

    // Simular una imagen de perfil, reemplaza esta parte con tu lógica de carga de imágenes
    val profilePictureRes = R.drawable.img

    LaunchedEffect(null) {
        if (!fetched) {
            viewModel.getUserProfile(perfil)
            fetched = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Círculo para la imagen de perfil
        Card(
            modifier = Modifier
                .size(160.dp) // Aumenta el tamaño aquí
                .clip(CircleShape)
        ) {
            Image(
                painter = painterResource(id = profilePictureRes),
                contentDescription = null, // Puedes agregar una descripción
                modifier = Modifier
                    .size(160.dp) // Ajusta el tamaño aquí
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Información del perfil en bloques
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text("Nombre: ${perfil.value.firstName}")
                Text("Apellido: ${perfil.value.lastName}")
                Text("Email: ${perfil.value.email}")
                Text("Username: ${perfil.value.username}")
                Text("Phone: ${perfil.value.phone}")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                viewModel.setLoginError("")
                viewModel.setToken("")
                viewModel.setLoggedOut()
                navController.navigate("LoginPage")
            }
        ) {
            Text("Log Out")
        }
    }
}


