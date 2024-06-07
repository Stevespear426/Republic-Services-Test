package com.republic.services.test.spear.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Route(
    val id: Int,
    val name: String,
    val type: String
)