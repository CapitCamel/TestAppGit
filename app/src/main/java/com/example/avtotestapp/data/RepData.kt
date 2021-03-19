package com.example.avtotestapp.data

import java.time.LocalDateTime

data class Result(
    val items: List<RepData>
)

data class RepData(
    val id: Int,
    val name: String,
    val stargazers_count: Int,
    val owner: Owner,
    val description: String,
    val language: String,
    val updated_at: LocalDateTime
)

data class Owner(
        val id: Int,
        val avatar_url: String,
        val login: String
)

data class User(
        val id: Int,
        val login: String,
        val avatar_url: String,
        val bio: String,
        val twitter_username: String,
        val followers: Int,
        val following: Int
)