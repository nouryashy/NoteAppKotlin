package com.example.noteappkotlin

import androidx.lifecycle.MutableLiveData
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert
    fun addData(noteModel: NoteModel): Completable

    @Update
    fun update(noteModel: NoteModel): Completable

    @Delete
    fun delete(noteModel: NoteModel): Completable

    @Query("SELECT*FROM note_table")
    fun getAllNotes(): Single<List<NoteModel>>
}