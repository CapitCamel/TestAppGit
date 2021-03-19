package com.example.avtotestapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_INDEX = 1

class GitPagingSource(
    private val gitApi: GitEndpoints,
    private val query: String
) : PagingSource<Int, RepData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RepData> {
        val position = params.key ?: STARTING_INDEX
        return try {
            val response = gitApi.getRepositories(query, position, params.loadSize)
            val repos = response.items

            LoadResult.Page(
                data = repos,
                prevKey = if (position == STARTING_INDEX) null else position - 1,
                nextKey = if (repos.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, RepData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}

