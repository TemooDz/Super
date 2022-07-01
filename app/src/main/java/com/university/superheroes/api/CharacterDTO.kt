package com.university.superheroes.api

import com.google.gson.annotations.SerializedName

data class CharacterDTO(
    val name: String,
    val biography: BiographyDTO,
    val image: ImageDTO
)

data class BiographyDTO(
    @SerializedName("full-name")
    val fullName: String
)
