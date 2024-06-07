package com.republic.services.test.spear.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Driver(
    val id: String,
    val name: String
)