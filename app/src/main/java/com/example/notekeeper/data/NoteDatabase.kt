package com.example.notekeeper.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notekeeper.models.Note

//::class java --> meaning you get instance of class

@Database(entities = [Note::class],version =1, exportSchema = false)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao():NoteDao

    companion object{
        // singleton object --> this class only have 1 instance
        @Volatile
        private var INSTANCE:NoteDatabase? = null

        fun getDatabase(ctx:Context):NoteDatabase{
            val temInstance = INSTANCE
            if(temInstance != null){
                INSTANCE = temInstance
            }

            synchronized(this){
                val instance = Room.databaseBuilder(ctx.applicationContext,NoteDatabase::class.java,
                    "note_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}