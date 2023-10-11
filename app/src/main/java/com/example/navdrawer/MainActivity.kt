package com.example.navdrawer

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.NavDrawerTheme
import com.example.navdrawer.navigation.MainPage
import com.example.navdrawer.screens.about.AboutPage
import com.example.navdrawer.screens.favorites.FavsPage
import com.example.navdrawer.screens.home.HomePage
import com.example.navdrawer.screens.login.LoginPage
import com.example.navdrawer.screens.profile.ProfilePage
import com.example.navdrawer.screens.register.RegisterPage
import com.example.navdrawer.screens.seguridad.SecurityPage
import com.example.navdrawer.screens.tags.TagsPage


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        setContent {
            AppFrisaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: AppViewModel = viewModel()
                    val navController = rememberNavController()

                  /// viewModel.setLoggedIn()
                    NavHost(navController = navController, startDestination = if (viewModel.isUserLoggedIn()) "MainPage" else "LoginPage") {

                        composable("LoginPage") {
                            LoginPage(navController, viewModel)
                        }

                        composable("MainPage") {
                            MainPage()
                        }
                        composable("RegisterPage") {
                            RegisterPage(navController, viewModel)
                        }
                    }

                /*     if (viewModel.isUserLoggedIn()) {
                        // El usuario está autenticado, cargar la página de inicio con el navigation drawer
                       // MainPage(navController)

                    } else {
                        // El usuario no está autenticado, cargar la página de inicio de sesión
                        LoginPage(navController = navController, viewModel)
                    }*/
                    // Después de que el usuario haya completado el inicio de sesión
                    //viewModel.setLoggedIn()
                    // ...

                /*    NavHost(navController = navController, startDestination = "HomePage") {
                        // Otras composable destinations aquí...

                        composable("HomePage") {
                            HomePage(navController, viewModel)
                        }

                        composable(
                            "AboutPage/{orgname}",
                            arguments = listOf(
                                navArgument("orgname") { // Definir el argumento orgname
                                    type = NavType.StringType
                                }
                            )
                        ) { navBackStackEntry ->
                            val orgname = navBackStackEntry.arguments?.getString("orgname")
                            AboutPage(orgname)
                        }
                    }*/

                }
            }
        }
    }
}



