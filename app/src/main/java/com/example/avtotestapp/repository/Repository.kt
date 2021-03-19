package com.example.avtotestapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.avtotestapp.data.GitEndpoints
import com.example.avtotestapp.data.GitPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val gitEndpoints: GitEndpoints){
//    suspend fun getRepository(q: String) = gitEndpoints.getRepositories(q)

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { GitPagingSource(gitEndpoints, query) }
        ).liveData

    suspend fun getUser(login: String) = gitEndpoints.getUser(login)
}