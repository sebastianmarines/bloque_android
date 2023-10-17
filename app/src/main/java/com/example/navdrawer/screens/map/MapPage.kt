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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.navigation.NavHostController
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.MapPageModel
import com.example.navdrawer.OSCModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun MapPage(viewModel: AppViewModel) {
    var fetched by remember { mutableStateOf(false) }
    var data: MutableState<MapPageModel> = remember {
        mutableStateOf(
            MapPageModel(
                organizaciones = listOf(),
                tags = listOf(),
            )
        )
    }


    LaunchedEffect(null) {
        if (!fetched) {
            viewModel.getMapData(data)
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
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(12.dp))
                .padding(4.dp)
        ) {
            if (fetched && data.value.organizaciones.isNotEmpty()) {
                MapPageSection(data.value.organizaciones)
            }
        }
    }
}

@Composable
fun MapPageSection(orgs: List<OSCModel>) {
    Log.e("MapSection", "MapSection called")
    var location = LatLng(
        orgs[0].latitud,
        orgs[0].longitud,
    )
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
        val markerInfoWindowShownState = remember { mutableMapOf<Int, Boolean>() }
        for (org in orgs) {
            MarkerInfoWindow(
                state = MarkerState(
                    position = LatLng(org.latitud, org.longitud),
                ),
                onClick = { marker ->
                    markerInfoWindowShownState[org.id] = markerInfoWindowShownState[org.id] != true
                    if (markerInfoWindowShownState[org.id] == true) {
                        marker.showInfoWindow()
                    } else {
                        marker.hideInfoWindow()
                    }

                    return@MarkerInfoWindow false
                }
            ) { _ ->
                Card(
                    modifier = Modifier
                        .width(400.dp)
                        .padding(8.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp,
                    ),
                ) {
                    Column(
                        Modifier.padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = org.nombre,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = "Address",
                                modifier = Modifier.size(16.dp),
                            )
                            Text(
                                text = org.direccion,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Call,
                                contentDescription = "Phone",
                                modifier = Modifier.size(16.dp),
                            )
                            Text(
                                text = org.telefono,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Email",
                                modifier = Modifier.size(16.dp),
                            )
                            Text(
                                text = org.email,
                                style = MaterialTheme.typography.bodyMedium,
                            )
                        }
                    }
                }
            }
        }

    }
}
