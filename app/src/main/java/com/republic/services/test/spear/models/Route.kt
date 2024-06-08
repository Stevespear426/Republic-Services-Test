package com.republic.services.test.spear.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Route(
    @PrimaryKey
    val id: Int,
    val name: String,
    val type: String
)