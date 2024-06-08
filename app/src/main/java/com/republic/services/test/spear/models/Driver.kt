package com.republic.services.test.spear.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Driver(
    val id: String,
    val name: String
) {
    fun toEntity(): DriverEntity {
        val split = name.split(" ")
        return DriverEntity(id, split[0], split[1])
    }
}