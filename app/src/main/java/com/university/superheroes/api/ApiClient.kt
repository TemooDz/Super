package com.university.superheroes.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    fun apiService(): ApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.superheroapi.com/api.php/2896918083892052/")
            .build()
            .create(ApiService::class.java)
    }
}
