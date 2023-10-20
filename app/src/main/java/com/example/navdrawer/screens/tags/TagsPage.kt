package com.example.navdrawer.screens.tags

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navdrawer.AppViewModel
import com.example.navdrawer.TagModel

@Composable
fun TagsPage(viewModel: AppViewModel) {
    var tags = remember {
        mutableStateOf(listOf<TagModel>())
    }

    LaunchedEffect(null) {
        viewModel.getTags(tags)
    }


    Column(modifier = Modifier.padding(12.dp)) {
        tags.value.forEach {
            Button(onClick = { /*TODO*/ }) {
                Text(text = it.nombre)
            }
        }
    }

}