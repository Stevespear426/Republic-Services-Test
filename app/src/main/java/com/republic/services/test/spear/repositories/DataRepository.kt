package com.republic.services.test.spear.repositories

import com.republic.services.test.spear.models.Driver
import com.republic.services.test.spear.models.DriverEntity
import com.republic.services.test.spear.models.Route
import com.republic.services.test.spear.repositories.local.AppDatabase
import com.republic.services.test.spear.repositories.remote.ApiService
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val apiService: ApiService,
    private val db: AppDatabase
) {

    suspend fun getDriversASC(): List<DriverEntity> {
        val drivers = db.driverDao().getDriversASC()
        if (drivers.isNotEmpty()) return drivers
        fetchData()
        return db.driverDao().getDriversASC()
    }

    suspend fun getDriversDESC(): List<DriverEntity> {
        val drivers = db.driverDao().getDriversDESC()
        if (drivers.isNotEmpty()) return drivers
        fetchData()
        return db.driverDao().getDriversDESC()
    }

    suspend fun getRoutes(): List<Route> {
        val routes = db.routeDao().getRoutes()
        if (routes.isNotEmpty()) return routes
        fetchData()
        return db.routeDao().getRoutes()
    }

    suspend fun fetchData() {
        val response = apiService.getData()
        db.insertResponse(response)
    }
}