package com.example.notekeeper.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notekeeper.models.Note

// https://developer.android.com/topic/libraries/architecture/livedata

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(note: Note)

    @Update()
    suspend fun update(note: Note)

    @Query("DELETE FROM note_table Where id = :id")
    suspend fun delete(id:String)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM note_table")
    fun list():LiveData<List<Note>>

    @Query("SELECT * FROM note_table WHERE title LIKE :query")
    fun search(query:String):LiveData<List<Note>>
}