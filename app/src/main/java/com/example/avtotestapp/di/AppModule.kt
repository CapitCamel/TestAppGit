package com.example.avtotestapp.di

import com.example.avtotestapp.LocalDateTimeJsonDeserializer
import com.example.avtotestapp.data.GitEndpoints
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
//import dagger.hilt.android.components
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeJsonDeserializer())
            .create()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson) = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideGitApi(retrofit: Retrofit): GitEndpoints =
            retrofit.create(GitEndpoints::class.java)

}