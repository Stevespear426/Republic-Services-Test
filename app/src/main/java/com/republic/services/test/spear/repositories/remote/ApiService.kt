package com.republic.services.test.spear.repositories.remote

import com.republic.services.test.spear.models.DataResponse
import retrofit2.http.GET

interface ApiService {

    @GET("data")
    suspend fun getData(): DataResponse
}