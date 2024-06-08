package com.republic.services.test.spear.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Driver")
data class DriverEntity(
    @PrimaryKey
    val id: String,
    val firstName: String,
    val lastName: String
)