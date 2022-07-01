package com.university.superheroes.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{id}")
    fun getCharacter(@Path("id") characterId: Int): Call<CharacterDTO>

    @GET("search/{name}")
    fun search(@Path("name") name: String): Call<SearchDTO>
}