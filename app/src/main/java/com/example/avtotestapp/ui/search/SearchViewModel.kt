package com.example.avtotestapp.ui.search


import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*

import androidx.paging.cachedIn

import com.example.avtotestapp.repository.Repository


class SearchViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted state: SavedStateHandle
): ViewModel() {

    private val _navigateToSelectedProperty = MutableLiveData<String>()
    val navigateToSelectedProperty: LiveData<String>
        get() = _navigateToSelectedProperty

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val repositories = currentQuery.switchMap { queryString ->
        repository.getSearchResults(queryString).cachedIn(viewModelScope)
    }


    fun searchRepos(query: String) {
        currentQuery.value = query
    }

    fun refresh(){
        searchRepos(currentQuery.value.toString())
    }

    companion object {
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = ""
    }

    fun displayPropertyDetails(login: String) {
        _navigateToSelectedProperty.value = login
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

}