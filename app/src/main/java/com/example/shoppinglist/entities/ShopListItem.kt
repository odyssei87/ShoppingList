package com.example.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_list_item")
data class ShopListItem(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,
    @ColumnInfo(name = "name")               /*название что купить*/
    val name:String,
    @ColumnInfo(name = "itemInfo")           /*заметка про покупку*/
    val itemInfo:String = "",
    @ColumnInfo(name = "itemChecked")        /*(чекбокс)куплен ли товар, изначально стоит нет*/
    val itemChecked:Boolean = false,
    @ColumnInfo(name = "listId")             /*идентификатор списка к которому пренадлежит элемент*/
    val listId:Int,
    @ColumnInfo(name = "itemType")           /**/
    val itemType:Int = 0,
)
