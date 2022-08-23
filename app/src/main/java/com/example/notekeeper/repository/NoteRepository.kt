package com.example.notekeeper.repository

import androidx.lifecycle.LiveData
import com.example.notekeeper.data.NoteDao
import com.example.notekeeper.models.Note


// class to acces to multiple data source
class NoteRepository(private val noteDao: NoteDao) {
    var list:LiveData<List<Note>> = noteDao.list()
    suspend fun create(note:Note){
        return noteDao.create(note)
    }

    suspend fun update(note:Note){
        return noteDao.update(note)
    }

    suspend fun delete(id:String){
        return noteDao.delete(id)
    }

    suspend fun deleteAll(){
        return noteDao.deleteAll()
    }

     fun search(query:String):LiveData<List<Note>>{
        return noteDao.search(query)
    }
}