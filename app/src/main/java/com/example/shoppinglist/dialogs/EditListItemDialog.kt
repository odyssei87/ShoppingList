package com.example.shoppinglist.dialogs

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.EditListItemDialogBinding
import com.example.shoppinglist.entities.ShopListItem

object EditListItemDialog {
    @SuppressLint("SuspiciousIndentation")
    fun showDialog(context : Context, item : ShopListItem, listener : Listener){
        var dialog : AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = EditListItemDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            edName.setText(item.name)
            edInfo.setText(item.itemInfo)
            if (item.itemType==1) edInfo.visibility = View.GONE
                   bUpdate.setOnClickListener {
                       if (edName.text.toString().isNotEmpty()){
                            listener.onClick(item.copy(name = edName.text.toString(), itemInfo = edInfo.text.toString()))
                       }
                       dialog?.dismiss()
                   }
                }




        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }
    interface Listener{
        fun onClick(item : ShopListItem)
    }

}