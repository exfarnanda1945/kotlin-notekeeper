package com.example.notekeeper.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notekeeper.data.NoteDatabase
import com.example.notekeeper.models.Note
import com.example.notekeeper.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// comunicator between ui and repository
class NoteViewModel(app: Application) : AndroidViewModel(app) {

    val list:LiveData<List<Note>>
    private val repo:NoteRepository
    init {
        val noteDao = NoteDatabase.getDatabase(app).noteDao()
        repo = NoteRepository(noteDao)
        list = repo.list
    }

    fun create(note:Note){
        viewModelScope.launch(Dispatchers.IO) {
            repo.create(note)
        }
    }

    fun update(note:Note){
        viewModelScope.launch(Dispatchers.IO) {
            repo.update(note)
        }
    }

    fun delete(id:String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.delete(id)
        }
    }

    fun deleteAll(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteAll()
        }
    }

    fun search(query:String):LiveData<List<Note>>{
        return repo.search(query)
    }
}