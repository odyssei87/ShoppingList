package com.example.shoppinglist.fragments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.shoppinglist.R
import com.example.shoppinglist.activities.MainApp
import com.example.shoppinglist.activities.NewNoteActivity
import com.example.shoppinglist.databinding.FragmentNoteBinding
import com.example.shoppinglist.db.MainViewModel
import com.example.shoppinglist.db.NoteAdapter
import com.example.shoppinglist.entities.NoteItem


class NoteFragment : BaseFragment(), NoteAdapter.Listener {

    private lateinit var binding:FragmentNoteBinding
    private lateinit var editLauncher:ActivityResultLauncher<Intent>
    private lateinit var adapter: NoteAdapter
    private lateinit var defPref: SharedPreferences
    private  val mainViewModel : MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }
    override fun onClickNew() {
       editLauncher.launch(Intent(activity, NewNoteActivity::class.java))

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEditResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    private fun initRcView()= with(binding){
        defPref= activity?.let { PreferenceManager.getDefaultSharedPreferences(it) }!!
        rcViewNote.layoutManager = getLayoutManager()
        adapter = NoteAdapter(this@NoteFragment , defPref)
        rcViewNote.adapter = adapter
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager{
        return if (defPref.getString("not_style_key","Linear") == "Linear"){
            LinearLayoutManager(activity)
        }else{
            StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        }
    }

    private fun observer(){
        mainViewModel.allNotes.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun onEditResult(){
        editLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                val editState = it.data?.getStringExtra(EDIT_STATE_KEY)
                if (editState == "update"){
                    mainViewModel.updateNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)

                }else{
                    mainViewModel.insertNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)
                }

            }
        }
    }

    override fun deleteItem(id: Int) {
        mainViewModel.deleteNote(id)
    }

    override fun onClickItem(note: NoteItem) {
        val intent = Intent(activity ,NewNoteActivity::class.java).apply {
            putExtra(NEW_NOTE_KEY, note)
        }

        editLauncher.launch(intent)
    }

    companion object {
        const val NEW_NOTE_KEY = "new_note_key"
        const val EDIT_STATE_KEY = "edin_state_key"

        @JvmStatic
        fun newInstance() =
            NoteFragment()
    }

}
