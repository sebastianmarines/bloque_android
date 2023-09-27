package com.example.navdrawer.screens.about

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.OSCModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun AboutPage(orgId: String = "", navController: NavHostController, viewModel: AppViewModel) {
    var fetched by remember { mutableStateOf(false) }
    var organizacion: MutableState<OSCModel> = remember {
        mutableStateOf(
            OSCModel(
                0,
                "",
                "",
                "",
                "",
                "",
                "",
                listOf(),
                0.0,
                0.0,
            )
        )
    }


    LaunchedEffect(null) {
        if (!fetched) {
            viewModel.getOrganizacion(orgId, organizacion)
        }
        fetched = true
    }


    // The same as above but with the organization information on top and the map on the bottom
    Column(Modifier.fillMaxSize()) {
        // Organization information
        Column(Modifier.weight(1f)) {
            Text(text = "Organization: ${organizacion.value.nombre}")
            Text(text = "Address: ${organizacion.value.direccion}")
            Text(text = "Phone: ${organizacion.value.telefono}")
        }
        Box(Modifier.weight(1f)) {
            if (fetched && organizacion.value.nombre != "") {
                MapSection(organizacion.value)
            }
        }

    }
}

@Composable
fun MapSection(org: OSCModel) {
    Log.e("MapSection", "MapSection called")
    Log.e("MapSection", "Org: $org")
    var location = LatLng(org.latitud, org.longitud)
    Log.e("MapSection", "Location: $location")
    var cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            location,
            17f,
        )
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        Marker(
            state = MarkerState(
                position = location,
            ),
            title = org.nombre,
            snippet = org.direccion,
        )
    }
}
