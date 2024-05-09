package com.example.shoppinglist.fragments

import androidx.appcompat.app.AppCompatActivity
import com.example.shoppinglist.R

object FragmentManager {
    var currentFrag : BaseFragment?=null           /* переменная для сохранения текущего активного фрагмента*/

    fun setFragment(newFrag:BaseFragment, activity: AppCompatActivity){           /*функция для переключения между фрагментами */
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, newFrag)
        transaction.commit()
        currentFrag = newFrag
    }

}