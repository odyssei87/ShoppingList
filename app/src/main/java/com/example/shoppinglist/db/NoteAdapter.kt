package com.example.shoppinglist.db

import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.NoteListItemBinding
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.utlis.HtmlManager
import com.example.shoppinglist.utlis.TimeManager

class NoteAdapter(private val listener : Listener, private val defPref : SharedPreferences) : ListAdapter<NoteItem, NoteAdapter.ItemHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {        /*создаем holder */
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {                     /*заполнение разметки */
        holder.setData(getItem(position), listener, defPref)
    }
    
    class ItemHolder(view:View) : RecyclerView.ViewHolder(view){        /* класс который хранит ссылку на разметку (view) */
        private val binding = NoteListItemBinding.bind(view)
        fun setData(note: NoteItem , listener: Listener,defPref : SharedPreferences) = with(binding){
            tvTitle.text = note.title
            tvDescription.text= HtmlManager.getFromHtml(note.content).trim()
            tvTime.text = TimeManager.getTimeFormat(note.time, defPref)
            imDelete.setOnClickListener {
                listener.deleteItem(note.id!!)
                }
            itemView.setOnClickListener {
                listener.onClickItem(note)
            }
        }
        companion object{                                                         /* функция будет выдавать инициализированный класс ItemHolder */
            fun create(parent: ViewGroup):ItemHolder{
                return ItemHolder(LayoutInflater.from(parent.context).inflate
                    (R.layout.note_list_item,parent, false))
            }
        }
    }

    class ItemComparator:DiffUtil.ItemCallback<NoteItem>(){                                /*сравнение items  */
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem==newItem
        }

    }

    interface Listener{
        fun deleteItem(id:Int)
        fun onClickItem(note: NoteItem)
    }



}