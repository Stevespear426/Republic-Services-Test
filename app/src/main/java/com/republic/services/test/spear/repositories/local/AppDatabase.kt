package com.republic.services.test.spear.repositories.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.republic.services.test.spear.models.DataResponse
import com.republic.services.test.spear.models.DriverEntity
import com.republic.services.test.spear.models.Route

@Database(entities = [DriverEntity::class, Route::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun driverDao(): DriverDao
    abstract fun routeDao(): RouteDao

    fun insertResponse(response: DataResponse) {
        with(driverDao()) {
            delete()
            insertAll(response.drivers.map { it.toEntity() })
        }
        with(routeDao()) {
            delete()
            insertAll(response.routes)
        }
    }
}