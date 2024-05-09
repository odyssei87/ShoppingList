package com.example.shoppinglist.activities

import android.app.Application
import com.example.shoppinglist.db.MainDataBase

class MainApp : Application() {
    val database by lazy { MainDataBase.getDataBase(this) }         /*когда database ==null выполнится by lazy и вернет инстанцию(создаст базу).
                                                                              при следующем запуске код by lazy уже не запускается */
}