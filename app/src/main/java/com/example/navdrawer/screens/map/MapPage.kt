package com.example.navdrawer.screens.about

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.MapPageModel
import com.example.navdrawer.OSCModel
import com.example.navdrawer.TagModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@OptIn(ExperimentalMaterial3Api::class)
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
    var organizacionesList: MutableState<List<OSCModel>> = remember {
        mutableStateOf(listOf())
    }

    LaunchedEffect(null) {
        if (!fetched) {
            viewModel.getMapData(data, organizacionesList)
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
        Column(
            Modifier.padding(4.dp)
        ) {
            Text(text = "Organizaciones")
            val options = data.value.tags.map { it.nombre }
            var expanded by remember { mutableStateOf(false) }
            var selectedOptionText by remember { mutableStateOf("") }

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    readOnly = true,
                    value = selectedOptionText,
                    onValueChange = { },
                    label = { Text("Label") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(text = selectionOption) },
                            onClick = {
                                selectedOptionText = selectionOption
                                // Filter data
                                organizacionesList.value = data.value.organizaciones.filter { osc ->
                                    osc.tags.any { tag -> tag.nombre == selectionOption }
                                }
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
        Box(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(12.dp))
                .padding(4.dp)
        ) {
            var location = LatLng(25.650993, -100.28938)
            var cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(
                    location,
                    10f,
                )
            }
            Log.e("MapSection", "MapSection called")
            Log.e("MapSection", "Location: $location")

            var organizaciones by remember { mutableStateOf(data.value.organizaciones) }

            LaunchedEffect(organizacionesList) {
                organizaciones = organizacionesList.value
            }

            LaunchedEffect(data) {
                Log.e("MapSection", "LaunchedEffect called")
                if (data.value.organizaciones.isNotEmpty()) {
                    Log.e("MapSection", "LaunchedEffect called with data")
                    location = LatLng(
                        data.value.organizaciones[0].latitud,
                        data.value.organizaciones[0].longitud,
                    )
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
                        location,
                        17f,
                    )
                } else {
                    Log.e("MapSection", "LaunchedEffect called without data")
                    location = LatLng(0.0, 0.0)
                }
                Log.e("MapSection", "Location: $location")
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
            ) {
                val markerInfoWindowShownState = remember { mutableMapOf<Int, Boolean>() }
                for (org in organizacionesList.value) {
                    MarkerInfoWindow(
                        state = MarkerState(
                            position = LatLng(org.latitud, org.longitud),
                        ),
                        onClick = { marker ->
                            markerInfoWindowShownState[org.id] =
                                markerInfoWindowShownState[org.id] != true
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
    }
}


