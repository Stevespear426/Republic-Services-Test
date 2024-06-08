package com.republic.services.test.spear.repositories.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.republic.services.test.spear.models.Driver
import com.republic.services.test.spear.models.Route

@Dao
interface RouteDao {

    @Query("SELECT * FROM route")
    fun getRoutes(): List<Route>

    @Insert
    fun insertAll(routes: List<Route>)

    @Query("DELETE FROM Route")
    fun delete()
}