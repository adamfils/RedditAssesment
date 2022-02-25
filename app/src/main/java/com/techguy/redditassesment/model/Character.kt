package com.techguy.redditassesment.model

data class Character(
    val name: String,
    val image: String,
    val status: String,
    val species: String,
    val gender: String,
    val origin: Origin,
    val location: Origin
)

data class Origin(
    val name: String,
    val url: String,
)

data class CharacterResults(
    val results: ArrayList<Character>
)