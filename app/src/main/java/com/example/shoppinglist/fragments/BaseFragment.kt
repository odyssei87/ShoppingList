package com.example.shoppinglist.fragments

import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment(){                   /*класс с переопределяемой функцией для создания нового списка  */
    abstract fun onClickNew()
}