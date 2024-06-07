package com.republic.services.test.spear.presenters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.republic.services.test.spear.R

@Composable
fun DriversScreen(
    viewModel: DriversScreenViewModel = hiltViewModel(),
    onDriverClick: (driverId: String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    with(state) {
        when {
            loading -> {
                Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            drivers.isNotEmpty() -> {
                LazyColumn(Modifier.fillMaxSize()) {
                    items(drivers) {
                        TextButton(onClick = { onDriverClick(it.id) }) {
                            Text(it.name)
                        }
                    }
                }
            }

            else -> {
                Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
                    Text(stringResource(id = R.string.drivers_not_found))
                }
            }
        }
    }
}