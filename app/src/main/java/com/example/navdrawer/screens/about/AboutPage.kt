package com.example.navdrawer.screens.about

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun AboutPage(orgId: String = "", navController: NavHostController) {
    val tecDeMonterrey = LatLng(25.650835, -100.289029)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            tecDeMonterrey,
            17f,
        )
    }
    var fetched by remember { mutableStateOf(false) }


    // The same as above but with the organization information on top and the map on the bottom
    Column(Modifier.fillMaxSize()) {
        // Organization information
        Column(Modifier.weight(1f)) {
            Text(text = "Organization: A")
            Text(text = "Address: A")
            Text(text = "Phone: A")
        }
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(
                tecDeMonterrey,
                17f,
            )
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
                    title = "A",
                    snippet = "A",
                )
            }
        }

    }
}
