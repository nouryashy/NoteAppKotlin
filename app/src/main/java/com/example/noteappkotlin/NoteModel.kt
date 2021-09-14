package com.example.noteappkotlin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_table")
class NoteModel(
    @PrimaryKey(autoGenerate = true) val id: Int ,
    @ColumnInfo(name = "Title") val title: String?,
    @ColumnInfo(name = "Description") val description: String?
) : Serializable {


}