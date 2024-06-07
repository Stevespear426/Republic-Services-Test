package com.republic.services.test.spear.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataResponse(
    val drivers: List<Driver>,
    val routes: List<Route>
)