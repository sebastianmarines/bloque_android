package com.example.navdrawer.screens.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun AboutPage(orgname: String = "Sin Nombre", navController: NavHostController) {
    val tecDeMonterrey = LatLng(25.650835, -100.289029)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            tecDeMonterrey,
            17f,
        )
    }
//
//    // Map and organization information with scroll
//    // The map should only be visible when the user scrolls to the bottom and use half screen
//    Column(Modifier.fillMaxSize()) {
//        Box(Modifier.weight(1f)) {
//            GoogleMap(
//                modifier = Modifier.fillMaxSize(),
//                cameraPositionState = cameraPositionState,
//            ) {
//                Marker(
//                    state = MarkerState(
//                        position = tecDeMonterrey,
//                    ),
//                    title = "Tec de Monterrey",
//                    snippet = "Campus Monterrey",
//                )
//            }
//        }
//
//        // Organization information
//        Column(Modifier.weight(1f)) {
//            Text(text = "Organization: $orgname")
//            Text(text = "Address: Av. Eugenio Garza Sada 2501, Tecnológico, 64849 Monterrey, N.L.")
//            Text(text = "Phone: 81 8358 2000")
//        }
//    }

    // The same as above but with the organization information on top and the map on the bottom
     Column(Modifier.fillMaxSize()) {
        // Organization information
        Column(Modifier.weight(1f)) {
            Text(text = "Organization: $orgname")
            Text(text = "Address: Av. Eugenio Garza Sada 2501, Tecnológico, 64849 Monterrey, N.L.")
            Text(text = "Phone: 81 8358 2000")
        }
        Box(Modifier.weight(1f)) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
            ) {
                Marker(
                    state = MarkerState(
                        position = tecDeMonterrey,
                    ),
                    title = "Tec de Monterrey",
                    snippet = "Campus Monterrey",
                )
            }
        }
     }
}