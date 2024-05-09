package com.example.shoppinglist.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglist.entities.LibraryItem
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShopListNameItem
import com.example.shoppinglist.entities.ShopListItem
import kotlinx.coroutines.flow.Flow

@Dao                                                          /*класс для записи и считывания базы данных */
interface Dao {
    @Query("SELECT * FROM note_list")                         /*функция для чтения */
    fun getAllNotes():Flow<List<NoteItem >>

    @Query("SELECT * FROM shopping_list_names")                         /*функция для чтения */
    fun getAllShopListName():Flow<List<ShopListNameItem >>

    @Query("SELECT * FROM shop_list_item WHERE listId LIKE:listId")                         /*функция для чтения */
    fun getAllShopListItems(listId : Int):Flow<List<ShopListItem >>

    @Query("SELECT * FROM library WHERE name LIKE :name")                         /*функция для чтения */
    suspend fun getAllLibraryItems(name: String):List<LibraryItem>

    @Insert                                                  /*функция для записи */
    suspend fun insertNote(note: NoteItem)

    @Insert                                                  /*функция для записи */
    suspend fun insertItem( shopListItem: ShopListItem)

    @Insert                                                  /*функция для записи */
    suspend fun insertLibraryItem( libraryItem: LibraryItem)

    @Insert                                                  /*функция для записи */
    suspend fun insertShopListName(name: ShopListNameItem)

    @Query("DELETE FROM note_list WHERE id IS :id")           /*функция для удаления */
    suspend fun deleteNote(id:Int)

    @Query("DELETE FROM library WHERE id IS :id")           /*функция для удаления */
    suspend fun deleteLibraryItem(id:Int)

    @Query("DELETE FROM shopping_list_names WHERE id IS :id")           /*функция для удаления */
    suspend fun deleteShopListName(id:Int)

    @Query("DELETE FROM shop_list_item WHERE listId LIKE:listId")
   suspend fun deleteShopItemsByListId(listId : Int)

    @Update
    suspend fun updateNote(note: NoteItem)

    @Update
    suspend fun updateLibraryItem(item: LibraryItem)

    @Update
    suspend fun updateListItem(item: ShopListItem)

    @Update
    suspend fun updateListName(shopListName: ShopListNameItem)
}