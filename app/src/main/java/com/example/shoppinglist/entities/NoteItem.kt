package com.example.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note_list")
data class NoteItem(
    @PrimaryKey(autoGenerate = true)
    val id :Int?,
    @ColumnInfo(name = "title")              /*заголовок*/
    val title :String,
    @ColumnInfo(name = "content")            /*сама заметка*/
    val content:String,
    @ColumnInfo(name = "time")               /*время*/
    val time :String,
    @ColumnInfo(name = "category")           /*категория , для фильтра*/
    val category: String
) : Serializable
