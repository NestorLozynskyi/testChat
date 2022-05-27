/*package com.example.chattest.api

import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/geocode/v1/json")
    suspend fun getCity(
        @Query("q") q: String,
        @Query("key") key: String = ""//Constants.CITY_TOKEN,
    ): Any?//CityResponse?
}*/