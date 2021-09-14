package com.example.noteappkotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteModel::class], version = 1)
 abstract class NoteDatabase :RoomDatabase(){
    abstract fun noteDao(): NoteDao

    class DataBaseManager {
        private var instance: NoteDatabase? = null

        @Synchronized
        fun getInstance(context: Context): NoteDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "note_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

            }
            return instance

        }
    }
}