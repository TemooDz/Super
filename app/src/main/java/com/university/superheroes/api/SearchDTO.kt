package com.university.superheroes.api

data class SearchDTO(
    val results: List<SearchCharactersDTO>
)

data class SearchCharactersDTO(
    val id: Int,
    val name: String,
    val image: ImageDTO
)

data class ImageDTO(
    val url: String
)
