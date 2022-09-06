package com.ykp.DiniBilmece.model

data class QuestionItem(
    val answer: String,
    val choices: List<String>,
    val question: String
)