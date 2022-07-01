package com.university.superheroes.fragments

data class Character(
    val name: String,
    val image: String,
    val clickAction: () -> Unit
)
