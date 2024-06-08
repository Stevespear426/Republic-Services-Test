package com.republic.services.test.spear.presenters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.republic.services.test.spear.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoutesScreen(
    driverId: String,
    viewModel: RoutesScreenViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = driverId) {
        viewModel.getRouteForDriver(driverId.toInt())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Driver #: $driverId") },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back Arrow",
                        )
                    }
                },
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it), Alignment.Center
        ) {
            with(state) {
                when {
                    loading -> CircularProgressIndicator(Modifier.testTag("Route Loading Test"))
                    route != null -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp)
                                .testTag("Route Column Test"),
                            verticalArrangement = Arrangement.Center,
                        ) {
                            with(route) {
                                Text(
                                    stringResource(R.string.route_info),
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Text(stringResource(R.string.route_id, id))
                                Text(stringResource(R.string.route_name, name))
                                Text(stringResource(R.string.route_type, type))
                            }
                        }
                    }
                    else -> Text(stringResource(id = R.string.route_not_found), Modifier.testTag("Route Error Test"))
                }
            }
        }
    }
}