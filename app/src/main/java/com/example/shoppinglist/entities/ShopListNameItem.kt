package com.example.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "shopping_list_names")              /*Таблица Названий создаваемого списка покупок*/
data class ShopListNameItem(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    @ColumnInfo(name = "name")                          /*название*/
    val name:String,
    @ColumnInfo(name = "time")                          /*время*/
    val time:String,
    @ColumnInfo(name = "allItemCounter")
    val allItemCounter:Int,
    @ColumnInfo(name = "checkItemsCounter")
    val checkItemsCounter:Int,
    @ColumnInfo(name = "itemsIds")
    val itemsIds:String,

    ):Serializable
