package com.devlight.school.model.network

import com.devlight.school.model.entity.DrinkApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DrinkApiService {

    @GET("api/json/v1/1/search.php")
    fun getDrinksByName(@Query("s") drinkName: String?): Call<DrinkApiResponse?>
}