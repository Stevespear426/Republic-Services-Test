package com.republic.services.test.spear.presenters

import androidx.lifecycle.ViewModel
import com.republic.services.test.spear.models.Route
import com.republic.services.test.spear.repositories.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RoutesScreenViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {


    private val _state: MutableStateFlow<RoutesPageState> =
        MutableStateFlow(RoutesPageState(true))
    val state = _state.asStateFlow()


    suspend fun getRouteForDriver(driverId: Int) = withContext(Dispatchers.IO) {
        val routes = dataRepository.getRoutes()
        val routeMatch = routes.find { it.id == driverId }
        val route = when {
            routeMatch != null -> routeMatch
            driverId.rem(2) == 0 -> routes.first { it.type == "R" }
            driverId.rem(5) == 0 -> routes.filter { it.type == "C" }[1]
            else -> routes.last { it.type == "I" }
        }
        _state.value = RoutesPageState(route = route)
    }

}

data class RoutesPageState(
    val loading: Boolean = false,
    val route: Route? = null
)
