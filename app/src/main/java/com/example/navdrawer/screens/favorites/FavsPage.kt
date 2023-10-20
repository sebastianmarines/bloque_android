package com.example.navdrawer.screens.favorites

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.OSCModel
import com.example.navdrawer.screens.home.OrgRow

@Composable
fun FavsPage(navController: NavController, viewModel: AppViewModel) {
    var organizaciones = remember {
        mutableStateOf(listOf<OSCModel>())
    }

    Column {
        Text(text = "Welcome to HomePage")
    }

    LaunchedEffect(null) {
        viewModel.getFavoritos(organizaciones)
    }

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {

        Column(modifier = Modifier.padding(12.dp)) {
            LazyColumn {
                items(items = organizaciones.value) {
                    OrgRow(orgname = it.nombre) { orgname ->
                        Log.d("Organizaciones", "$orgname")
                        navController.navigate("AboutPage/${it.id}")
                    }
                }
            }
        }
    }
}