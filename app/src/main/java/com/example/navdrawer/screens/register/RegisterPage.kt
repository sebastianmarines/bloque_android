package com.example.navdrawer.screens.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RegisterPage(navController: NavController) {
    Column {
        Text(text = "Aquí se registra el usuario")
        // Aquí puedes agregar elementos de UI para el registro
    }

    Button(
        onClick = {
            // Redirige a la página de registro (RegisterPage)
            navController.navigate("TagsPage")
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Registro Listo")
    }
}
