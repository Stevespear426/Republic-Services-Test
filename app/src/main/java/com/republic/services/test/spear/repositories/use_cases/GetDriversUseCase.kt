package com.republic.services.test.spear.repositories.use_cases

import com.republic.services.test.spear.models.DriverEntity
import com.republic.services.test.spear.models.SortOptions
import com.republic.services.test.spear.repositories.DataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetDriversUseCase @Inject constructor(
    private val dataRepository: DataRepository
) {
    operator fun invoke(sort: SortOptions): Flow<List<DriverEntity>> = flow {
        when (sort) {
            SortOptions.ASC -> emit(dataRepository.getDriversASC())
            else -> emit(dataRepository.getDriversDESC())
        }
    }.flowOn(Dispatchers.IO).catch {
        emit(emptyList())
    }
}