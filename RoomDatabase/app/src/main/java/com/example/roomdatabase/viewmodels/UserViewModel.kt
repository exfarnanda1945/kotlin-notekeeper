package com.example.roomdatabase.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.data.UserDatabase
import com.example.roomdatabase.repository.UserRepository
import com.example.roomdatabase.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application):AndroidViewModel(application) {

    val list: LiveData<List<User>>
    private val repo : UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repo = UserRepository(userDao)
        list = repo.list
    }

    fun create(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repo.create(user)
        }
    }

    fun update(user:User){
        viewModelScope.launch(Dispatchers.IO) {
            repo.update(user)
        }
    }

    fun delete(user:User){
        viewModelScope.launch(Dispatchers.IO) {
            repo.delete(user)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAll()
        }
    }
}