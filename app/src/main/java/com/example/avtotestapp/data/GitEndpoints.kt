package com.example.avtotestapp.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitEndpoints {

    @GET("/search/repositories")   //"/search/repositories?q=avito+language:kotlin"
    suspend fun getRepositories(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Result

    @GET("/users/{username}")
    suspend fun getUser(@Path("username") login: String): User
}

