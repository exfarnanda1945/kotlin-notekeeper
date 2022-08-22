package com.example.roomdatabase.repository

import androidx.lifecycle.LiveData
import com.example.roomdatabase.data.UserDao
import com.example.roomdatabase.models.User

class UserRepository(private val userDao: UserDao) {
    val list:LiveData<List<User>> = userDao.list()

    suspend fun create(user: User){
        userDao.create(user)
    }

    suspend fun update(user:User){
        userDao.update(user)
    }

    suspend fun delete(user:User){
        userDao.delete(user)
    }

    suspend fun deleteAll(){
        userDao.deleteAll()
    }
}