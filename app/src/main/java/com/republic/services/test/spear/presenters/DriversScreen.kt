package com.republic.services.test.spear.presenters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.republic.services.test.spear.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriversScreen(
    viewModel: DriversScreenViewModel = hiltViewModel(),
    onDriverClick: (driverId: String) -> Unit
) {

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text(stringResource(R.string.drivers)) }) }
    ) {
        DriversMainContent(it) { driverId -> onDriverClick(driverId) }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DriversMainContent(
    paddingValues: PaddingValues,
    viewModel: DriversScreenViewModel = hiltViewModel(),
    onDriverClick: (driverId: String) -> Unit
) {
    val state by viewModel.state.collectAsState()

    with(state) {

        val refreshScope = rememberCoroutineScope()

        fun refresh() = refreshScope.launch {
            viewModel.refreshData()
        }

        val refreshState = rememberPullRefreshState(loading, ::refresh)

        Box(
            Modifier
                .pullRefresh(refreshState)
                .padding(paddingValues)
        ) {
            when {
                drivers.isNotEmpty() -> {
                    LazyColumn(
                        Modifier
                            .pullRefresh(refreshState)
                            .fillMaxSize()
                    ) {
                        items(drivers) {
                            TextButton(onClick = { onDriverClick(it.id) }) {
                                Text("${it.firstName} ${it.lastName}")
                            }
                        }
                    }
                }

                loading -> {
                    Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                else -> {
                    Box(modifier = Modifier.fillMaxSize(), Alignment.Center) {
                        Text(stringResource(id = R.string.drivers_not_found))
                    }
                }
            }
            PullRefreshIndicator(
                loading, refreshState, Modifier.align(
                    Alignment.TopCenter
                )
            )
        }
    }
}
