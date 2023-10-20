package com.example.navdrawer.screens.about

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.AppLaunchChecker
import androidx.navigation.NavHostController
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.OSCModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState


@Composable
fun EditPage(navController: NavHostController, viewModel: AppViewModel) {
    val id = "2"
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
    val markerState = rememberMarkerState(position = LatLng(0.0, 0.0))

    LaunchedEffect(null) {
        if (!fetched) {
            viewModel.getOrganizacion(id, organizacion, markerState)
        }
        fetched = true
    }

    // The same as above but with the organization information on top and the map on the bottom
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(Modifier.padding(16.dp)) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.AccountCircle, contentDescription = null, Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Organization: ${organizacion.value.nombre}")
                }

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Place, contentDescription = null, Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Address: ${organizacion.value.direccion}")
                }

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Call, contentDescription = null, Modifier.size(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Phone: ${organizacion.value.telefono}")
                }
            }
        }

        TextButton(onClick = {
            viewModel.editOrg(id, markerState.position.latitude, markerState.position.longitude)
        }) {
            Text(text = "Edit")
        }

        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(12.dp))
                .padding(4.dp)
        ) {
            if (fetched && organizacion.value.nombre != "") {
                Log.e("MapSection", "MapSection called")
                Log.e("MapSection", "Org: ${organizacion.value.nombre}")
                var location = LatLng(organizacion.value.latitud, organizacion.value.longitud)
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
                    var isMarkerDraggable by remember { mutableStateOf(true) }

                    Marker(
                        draggable = isMarkerDraggable,
                        state = markerState,
                        title = organizacion.value.nombre,
                        snippet = organizacion.value.direccion,
                    )
                }
            }
        }
    }
}
