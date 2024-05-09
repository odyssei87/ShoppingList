package com.example.shoppinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.entities.LibraryItem
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.entities.ShopListItem
import com.example.shoppinglist.entities.ShopListNameItem

@Database(entities = [LibraryItem::class, NoteItem::class,
    ShopListItem::class, ShopListNameItem::class], version = 1)
abstract class MainDataBase : RoomDatabase(){                             /*Класс создания базы данных */
abstract fun getDao():Dao                                                  /* функция для доступа к базе данных*/

    companion object{
        @Volatile
        private var INSTANCE : MainDataBase? = null
        fun getDataBase(context : Context): MainDataBase{           /* функция получения инстанции базы данных. Если базы нет , то создается, если есть  то передается инстанция*/
            return INSTANCE ?: synchronized(this) {            /* synchronized() - используется чтобы обеспечить выполнение кода кокда несколько потоков пытаются его запустить одновременно один раз  */
                val instance = Room.databaseBuilder(context.applicationContext,
                    MainDataBase::class.java,
                    name = "shopping_list.db"
                ).build()
                    instance
            }
        }
    }

}