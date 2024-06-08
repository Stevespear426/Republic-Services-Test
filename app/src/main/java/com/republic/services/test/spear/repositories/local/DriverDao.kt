package com.republic.services.test.spear.repositories.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.republic.services.test.spear.models.DriverEntity

@Dao
interface DriverDao {

    @Query("SELECT * FROM driver ORDER BY lastName ASC")
    fun getDriversASC(): List<DriverEntity>

    @Query("SELECT * FROM driver ORDER BY lastName DESC")
    fun getDriversDESC(): List<DriverEntity>

    @Insert
    fun insertAll(drivers: List<DriverEntity>)

    @Query("DELETE FROM Driver")
    fun delete()
}