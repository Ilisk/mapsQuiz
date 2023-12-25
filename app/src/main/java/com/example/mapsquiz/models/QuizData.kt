package com.example.mapsquiz.models

import androidx.annotation.DrawableRes

data class QuizData(
    val city : String,
    @DrawableRes
    val image: Int,
    val question : String,
    val answers: Map<String, Boolean>

)


