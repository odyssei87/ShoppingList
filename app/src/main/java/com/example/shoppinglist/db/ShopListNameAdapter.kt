package com.example.shoppinglist.db

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ListNameItemBinding
import com.example.shoppinglist.entities.ShopListNameItem

class ShopListNameAdapter(private val listener : Listener) : ListAdapter<ShopListNameItem, ShopListNameAdapter.ItemHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {        /*создаем holder */
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {                     /*заполнение разметки */
        holder.setData(getItem(position), listener)
    }
    
    class ItemHolder(view:View) : RecyclerView.ViewHolder(view){        /* класс который хранит ссылку на разметку (view) */
        private val binding = ListNameItemBinding.bind(view)
        fun setData(shopListNameItem: ShopListNameItem, listener : Listener) = with(binding){
            tvListName.text = shopListNameItem.name
            tvTime.text = shopListNameItem.time
            pBar.max = shopListNameItem.allItemCounter
            pBar.progress = shopListNameItem.checkItemsCounter
            val colorState = ColorStateList.valueOf(getProgressColorState(shopListNameItem, binding.root.context))
            pBar.progressTintList = colorState
            counterCard.backgroundTintList=colorState
            val counterText = "${shopListNameItem.checkItemsCounter}/${shopListNameItem.allItemCounter}"
            tvCounter.text = counterText
            imDelete.setOnClickListener {
                listener.deleteItem(shopListNameItem.id!!)
                }
            imEdit.setOnClickListener {
                listener.editItem(shopListNameItem)
            }

            itemView.setOnClickListener {
                listener.onClickItem(shopListNameItem)

            }
        }
        private fun getProgressColorState(item : ShopListNameItem, context: Context): Int{
            return if (item.checkItemsCounter==item.allItemCounter){
                ContextCompat.getColor(context , R.color.green_main)
            }else{
                ContextCompat.getColor(context , R.color.red_main)
            }
        }

        companion object{                                                         /* функция будет выдавать инициализированный класс ItemHolder */
            fun create(parent: ViewGroup):ItemHolder{
                return ItemHolder(LayoutInflater.from(parent.context).inflate
                    (R.layout.list_name_item,parent, false))
            }
        }
    }

    class ItemComparator:DiffUtil.ItemCallback<ShopListNameItem>(){                                /*сравнение items  */
        override fun areItemsTheSame(oldItem:ShopListNameItem, newItem: ShopListNameItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShopListNameItem, newItem: ShopListNameItem): Boolean {
            return oldItem==newItem
        }

    }

    interface Listener{
        fun deleteItem(id:Int)
        fun editItem(shopListName : ShopListNameItem)
        fun onClickItem(shopListName: ShopListNameItem)
    }



}