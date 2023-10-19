package com.example.navdrawer.screens.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.OSCModel






@Composable
fun HomePage(
    navController: NavController,
    viewModel: AppViewModel,
) {


    val loggedIn = remember {
        mutableStateOf(viewModel.isUserLoggedIn())
    }

    var organizaciones = remember {
        mutableStateOf(listOf<OSCModel>())
    }

    Column {
        Text(text = "Welcome to HomePage")

    }

    LaunchedEffect(null) {
        viewModel.getOrganizaciones(organizaciones)
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

@Composable
fun OrgRow(orgname: String, onItemClick: (String) -> Unit = {}) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .height(130.dp)
            .clickable {
                onItemClick(orgname)
            },
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp),
                shape = RectangleShape
            ) {
                Icon(imageVector = Icons.Default.AccountBox, contentDescription = null)
            }
            Text(text = orgname)
        }
    }
}



