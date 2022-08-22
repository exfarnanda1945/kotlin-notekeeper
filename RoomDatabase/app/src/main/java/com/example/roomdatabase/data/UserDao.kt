package com.example.roomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomdatabase.models.User

// remove suspend function from dao
// https://stackoverflow.com/questions/62301115/error-type-of-the-parameter-must-be-a-class-annotated-with-entity-or-a-collect

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun create(user: User)

    @Update()
    suspend fun update(user:User)

    @Delete
    suspend fun delete(user:User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun list():LiveData<List<User>>
}