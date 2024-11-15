package com.example.retrofit.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Animal(
    val name: String,
    val type: String,
    val description: String,
    @SerialName("img_src")
    val imgSrc: String
)
