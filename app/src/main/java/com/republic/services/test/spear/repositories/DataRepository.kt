package com.republic.services.test.spear.repositories

import com.republic.services.test.spear.models.Driver
import com.republic.services.test.spear.models.Route
import com.republic.services.test.spear.repositories.remote.ApiService
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getDrivers(): List<Driver> {
        val response = apiService.getData()
        return response.drivers
    }

    suspend fun getRoutes(): List<Route> {
        val response = apiService.getData()
        return response.routes
    }

}