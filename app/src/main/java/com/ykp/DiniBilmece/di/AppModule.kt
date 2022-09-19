package com.ykp.DiniBilmece.di

import com.ykp.DiniBilmece.network.QuestionApi
import com.ykp.DiniBilmece.repository.QuestionRepository
import com.ykp.DiniBilmece.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {


    @Singleton
    @Provides
    fun provideQuestionRepository(api: QuestionApi) = QuestionRepository(api)




    @Singleton
    @Provides
    fun provideQuestionApi(): QuestionApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuestionApi::class.java)
    }
}