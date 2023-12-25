package com.example.mapsquiz.common
import com.example.mapsquiz.R
import com.example.mapsquiz.models.QuizData

val QUIZ_DATA = listOf(
    QuizData("Париж", R.drawable.paris,"Что изображено на фото?",mapOf(
        "Нотр-Дам-де-Пари" to true,
        "Парижская опера" to false,
        "Сент-Шапель" to false,
        "Сен-Жермен-де-Пре" to false
    )),
    QuizData("Москва", R.drawable.moscow,"Где находится эта станция Метро?",mapOf(
        "Сокольники" to true,
        "Тверская" to false,
        "Перово" to false,
        "Лубянка" to false
    )),
    QuizData("Сидней", R.drawable.sidney,"Главная достопримечательность Сиднея?",mapOf(
        "Харбор Бридж" to true,
        "Порт-Джексон" to false,
        "Леннокс-Бридж" to false,
        "Анзак" to false
    ))



    )