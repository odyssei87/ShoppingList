package com.example.shoppinglist.db

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ListNameItemBinding
import com.example.shoppinglist.databinding.ShopLibraryLisiItemBinding
import com.example.shoppinglist.databinding.ShopLisiItemBinding
import com.example.shoppinglist.entities.ShopListNameItem
import com.example.shoppinglist.entities.ShopListItem

class ShopListItemAdapter(private val listener : Listener) : ListAdapter<ShopListItem, ShopListItemAdapter.ItemHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {        /*создаем holder */

        return if(viewType==0)
            ItemHolder.createShopItem(parent)
        else
            ItemHolder.createLibraryItem(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {                     /*заполнение разметки */
        if(getItem(position).itemType==0) {
            holder.setItemData(getItem(position), listener)
        }else {
            holder.setLibraryData(getItem(position), listener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }
    
    class ItemHolder(val view:View) : RecyclerView.ViewHolder(view){        /* класс который хранит ссылку на разметку (view) */

        fun setItemData(shopListItem: ShopListItem, listener : Listener){
            val binding = ShopLisiItemBinding.bind(view)
            binding.apply {
                tvName.text = shopListItem.name
                tvInfo.text = shopListItem.itemInfo
                tvInfo.visibility = infoVisibility(shopListItem)
                chBox.isChecked = shopListItem.itemChecked
                setPaintFlagAndColor(binding)
                chBox.setOnClickListener {
                    listener.onClickItem(shopListItem.copy(itemChecked = chBox.isChecked), CHECK_BOX)
                }
                imEdit.setOnClickListener {
                    listener.onClickItem(shopListItem, EDIT)
                }
            }
        }

        fun setLibraryData(shopListItem: ShopListItem, listener : Listener) {
            val binding = ShopLibraryLisiItemBinding.bind(view)
            binding.apply {
                tvName.text = shopListItem.name
                imEdit.setOnClickListener {
                    listener.onClickItem(shopListItem, EDIT_LIBRARY_ITEM)
                }
                imDelete.setOnClickListener {
                    listener.onClickItem(shopListItem, DELETE_LIBRARY_ITEM)
                }
                itemView.setOnClickListener {
                    listener.onClickItem(shopListItem, ADD)
                }
            }
        }

        private fun setPaintFlagAndColor(binding: ShopLisiItemBinding){
            binding.apply {
                if (chBox.isChecked){
                    tvName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvInfo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvName.setTextColor(ContextCompat.getColor(binding.root.context , R.color.grey))
                    tvInfo.setTextColor(ContextCompat.getColor(binding.root.context , R.color.grey))
                }else{
                    tvName.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvInfo.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvName.setTextColor(ContextCompat.getColor(binding.root.context , R.color.black))
                    tvInfo.setTextColor(ContextCompat.getColor(binding.root.context , R.color.black))
                }
            }

        }

        private fun infoVisibility(shopListItem: ShopListItem) : Int{
            return if (shopListItem.itemInfo.isEmpty()){
                View.GONE
            } else{
                View.VISIBLE
            }

        }

        companion object{                                                         /* функция будет выдавать инициализированный класс ItemHolder */
            fun createShopItem(parent: ViewGroup):ItemHolder{
                return ItemHolder(LayoutInflater.from(parent.context).inflate
                    (R.layout.shop_lisi_item,parent, false))
            }
            fun createLibraryItem(parent: ViewGroup):ItemHolder{
                return ItemHolder(LayoutInflater.from(parent.context).inflate
                    (R.layout.shop_library_lisi_item,parent, false))
            }
        }
    }

    class ItemComparator:DiffUtil.ItemCallback<ShopListItem>(){                                /*сравнение items  */
        override fun areItemsTheSame(oldItem:ShopListItem, newItem: ShopListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShopListItem, newItem: ShopListItem): Boolean {
            return oldItem==newItem
        }

    }

    interface Listener{


        fun onClickItem(shopListItem: ShopListItem , state : Int)
    }

    companion object {

        const val EDIT = 0
        const val CHECK_BOX = 1
        const val EDIT_LIBRARY_ITEM = 2
        const val DELETE_LIBRARY_ITEM = 3
        const val ADD = 4
    }

}