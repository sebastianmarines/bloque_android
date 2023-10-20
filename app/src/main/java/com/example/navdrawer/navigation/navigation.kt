package com.example.navdrawer.navigation

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navdrawer.AppFrisaTheme
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.screens.about.AboutPage
import com.example.navdrawer.screens.about.EditPage
import com.example.navdrawer.screens.about.MapPage
import com.example.navdrawer.screens.favorites.FavsPage
import com.example.navdrawer.screens.home.HomePage
import com.example.navdrawer.screens.login.LoginPage
import com.example.navdrawer.screens.profile.ProfilePage
import com.example.navdrawer.screens.register.RegisterPage
import com.example.navdrawer.screens.seguridad.SecurityPage
import com.example.navdrawer.screens.tags.TagsPage

import kotlinx.coroutines.launch


data class NavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeCount: Int? = null,
    val route: String
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPage(viewModel: AppViewModel) {

//    val viewModel: AppViewModel = viewModel()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val userLoggedIn = viewModel.isUserLoggedIn()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    val items = listOf(
        NavigationItem(
            title = "Inicio",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = "HomePage"
        ),
        NavigationItem(
            title = "Mapa",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            route = "MapPage"
        ),
        NavigationItem(
            title = "Ver Perfil",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            route = "ProfilePage"
        ),
        NavigationItem(
            title = "Tags",
            selectedIcon = Icons.Filled.AddCircle,
            unselectedIcon = Icons.Outlined.AddCircle,
            route = "TagsPage"
        ),
        NavigationItem(
            title = "Favoritos",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.FavoriteBorder,
            route = "FavsPage"
        ),
        NavigationItem(
            title = "Seguridad",
            selectedIcon = Icons.Filled.Lock,
            unselectedIcon = Icons.Outlined.Lock,
            route = "SecurityPage"
        ),
        NavigationItem(
            title = "Editar",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
            route = "EditPage"
        )
    )
AppFrisaTheme {
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {

            ModalDrawerSheet {

                Spacer(modifier = Modifier.height(16.dp))

                items.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == selectedItemIndex,
                        onClick = {
                            navController.navigate(item.route)
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        badge = {
                            item.badgeCount?.let {
                                Text(text = item.badgeCount.toString())
                            }
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }) {
        Scaffold(
            topBar = {
                // Mover el TopAppBar aquí para que esté siempre presente
                TopAppBar(
                    title = { Text(text = "App Frisa") },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (drawerState.isClosed) {
                                scope.launch {
                                    drawerState.open()
                                }
                            } else {
                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Drawer Menu.")
                        }
                    }
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {


                NavHost(navController = navController, startDestination = "HomePage") {

                    composable("RegisterPage") {
                        RegisterPage(navController, viewModel)
                    }
                    composable("LoginPage") {
                        LoginPage(navController, viewModel)
                    }
                    composable("FavsPage") {
                        FavsPage(navController, viewModel)
                    }
                    composable("HomePage") {
                        HomePage(navController,viewModel)
                    }
                    composable("TagsPage") {
                        TagsPage(viewModel)
                    }
                    composable("ProfilePage") {
                        ProfilePage(navController, viewModel)
                    }
                    composable("SecurityPage") {
                        SecurityPage()
                    }

                    composable("MainPage") {
                        MainPage(viewModel)
                    }

                    composable("MapPage") {
                        MapPage(viewModel)
                    }

                    composable("EditPage") {
                        EditPage(navController, viewModel)
                    }

                    composable("AboutPage"+ "/{org}") {  backStackEntry ->
                        backStackEntry.arguments?.getString("org")
                            ?.let { AboutPage(it, navController, viewModel) }
                    }
                }

            }
        }
    }
}


}
