package com.republic.services.test.spear.presenters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.republic.services.test.spear.models.DriverEntity
import com.republic.services.test.spear.models.SortOptions
import com.republic.services.test.spear.repositories.DataRepository
import com.republic.services.test.spear.repositories.use_cases.GetDriversUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DriversScreenViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val getDriversUseCase: GetDriversUseCase
) : ViewModel() {

    private val _sort: MutableStateFlow<SortOptions> = MutableStateFlow(SortOptions.ASC)
    val sort = _sort.asStateFlow()

    private val _state: MutableStateFlow<DriversPageState> =
        MutableStateFlow(DriversPageState(true))
    val state = _state.asStateFlow()

    init {
        getDrivers()
    }

    private fun getDrivers() {
        sort.onEach {
            getDriversUseCase(it).onEach { drivers ->
                _state.value = DriversPageState(drivers = drivers)
            }.launchIn(viewModelScope)
        }.launchIn(viewModelScope)
    }

    fun setSortOptions(sortOptions: SortOptions) {
        _sort.value = sortOptions
    }

    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            dataRepository.fetchData()
        }
    }
}

data class DriversPageState(
    val loading: Boolean = false,
    val drivers: List<DriverEntity> = emptyList()
)
