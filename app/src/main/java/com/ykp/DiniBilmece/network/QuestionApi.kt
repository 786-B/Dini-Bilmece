package com.ykp.DiniBilmece.network

import com.ykp.DiniBilmece.model.Question
import retrofit2.http.GET
import javax.inject.Singleton


@Singleton
interface QuestionApi {
    @GET("sorular.json")
    suspend fun getAllQuestions(): Question
}