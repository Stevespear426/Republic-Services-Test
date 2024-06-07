package com.republic.services.test.spear.presenters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.republic.services.test.spear.models.Driver
import com.republic.services.test.spear.repositories.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriversScreenViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {


    private val _state: MutableStateFlow<DriversPageState> =
        MutableStateFlow(DriversPageState(true))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val drivers = dataRepository.getDrivers()
            _state.value = DriversPageState(drivers = drivers)
        }
    }
}

data class DriversPageState(
    val loading: Boolean = false,
    val drivers: List<Driver> = emptyList()
)
