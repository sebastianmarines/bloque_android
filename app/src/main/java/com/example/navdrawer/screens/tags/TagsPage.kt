package com.example.navdrawer.screens.tags

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TagsPage(navController: NavController) {
    Column {
        Text(text = "Aquí se muestran los tags")
    }

    Button(
        onClick = {
            // Redirige a la página de registro (RegisterPage)
            navController.navigate("HomePage")
        },
        modifier = Modifier.padding(16.dp)
    ) {
        Text("Listo")
    }

}