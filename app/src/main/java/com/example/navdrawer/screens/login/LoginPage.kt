package com.example.navdrawer.screens.login


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.navdrawer.AppViewModel
@Composable
fun LoginPage(navController: NavHostController, viewModel: AppViewModel) {
    if (!viewModel.isUserLoggedIn()) {
        // El usuario no está autenticado, mostrar la pantalla de inicio de sesión
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(text = "Login Page")

            Button(
                onClick = {
                    // Redirige a la página de inicio (HomePage) si se autentica con éxito
                    viewModel.setLoggedIn()
                    navController.navigate("MainPage")
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Hacer Login")
            }

            Button(
                onClick = {
                    // Redirige a la página de registro (RegisterPage)
                   navController.navigate("RegisterPage")
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Registro")
            }
        }
    } else {
        // El usuario está autenticado, redirigirlo a la página de inicio con el navigation drawer
     //   navController.navigate("HomePage")
    }
}

