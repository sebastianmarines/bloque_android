package com.example.navdrawer.screens.login


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.R


@Composable
fun LoginPage(navController: NavHostController, viewModel: AppViewModel) {
    if (!viewModel.isUserLoggedIn()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(16.dp).background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp)).padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.frisa_logo),
                    contentDescription = "Logo de Frisa",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(120.dp).padding(bottom = 16.dp)
                )
                // Logo o nombre de la aplicación, si lo tienes
                Text(text = "App Frisa")

                Spacer(modifier = Modifier.height(24.dp))

                // Campo de texto para correo
                var emailState by remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(
                    value = emailState,
                    onValueChange = { emailState = it },
                    label = { Text("Correo") },
                    placeholder = { Text("ejemplo@dominio.com") },
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        // Acciones al presionar "Done" en el teclado
                    }),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de texto para contraseña
                var passwordState by remember { mutableStateOf(TextFieldValue()) }
                OutlinedTextField(
                    value = passwordState,
                    onValueChange = { passwordState = it },
                    label = { Text("Contraseña") },
                    placeholder = { Text("Ingresa tu contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = {
                        // Acciones al presionar "Done" en el teclado
                    }),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Botón de inicio de sesión
                Button(onClick = {
                    // Autenticación y navegación
                    viewModel.setLoggedIn()
                    navController.navigate("MainPage")
                }) {
                    Text("Hacer Login")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para ir a la página de registro
                TextButton(onClick = {
                    navController.navigate("RegisterPage")
                }) {
                    Text("¿No tienes una cuenta? Regístrate")
                }
            }
        }
    }
}