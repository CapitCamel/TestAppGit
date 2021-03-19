package com.example.avtotestapp.ui.user

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.avtotestapp.data.User
import com.example.avtotestapp.repository.Repository
import kotlinx.coroutines.launch

class UserViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user




    fun getUser(login: String){
        viewModelScope.launch {
//            _user.value = GitApiService.buildService().getUser(login)
            _user.value = repository.getUser(login)
        }
    }


}