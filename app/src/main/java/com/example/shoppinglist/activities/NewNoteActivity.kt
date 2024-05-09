package com.example.shoppinglist.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityNewNoteBinding
import com.example.shoppinglist.entities.NoteItem
import com.example.shoppinglist.fragments.NoteFragment
import com.example.shoppinglist.utlis.HtmlManager
import com.example.shoppinglist.utlis.MyTouchListener
import com.example.shoppinglist.utlis.TimeManager

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NewNoteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNewNoteBinding
    private var note:NoteItem?=null
    private var pref : SharedPreferences? = null
    private lateinit var defPref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(getSelectedThem())
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        actionBarSettings()
        getNote()
        onClickColorPicker()
        init()
        setTextSize()
        actionMenuCallback()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    @SuppressLint("ClickableViewAccessibility")
    private fun init(){
        binding.colorPicker.setOnTouchListener(MyTouchListener())
        pref = PreferenceManager.getDefaultSharedPreferences(this)
    }

    private fun onClickColorPicker() = with(binding){
        imRed.setOnClickListener {
            setColorForSelectedText(R.color.picker_red)
        }
        imBlack.setOnClickListener {
            setColorForSelectedText(R.color.picker_black)
        }
        imBlue.setOnClickListener {
            setColorForSelectedText(R.color.picker_blue)
        }
        imGreen.setOnClickListener {
            setColorForSelectedText(R.color.picker_green)
        }
        imYellow.setOnClickListener {
            setColorForSelectedText(R.color.picker_yellow)
        }
        imOrenge.setOnClickListener {
            setColorForSelectedText(R.color.picker_orange)
        }
    }

    private fun getNote(){
        val sNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)
        if (sNote!=null) {
            note = sNote as NoteItem
            fillNote()
        }

    }

    private fun fillNote() = with(binding){

            edTitle.setText(note?.title)
            edDeccription.setText(HtmlManager.getFromHtml(note?.content!!).trim())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nw_note_menu , menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.id_save){
                setMainResult()
        }else if (item.itemId==android.R.id.home){
            finish()
        }else if (item.itemId==R.id.id_bold){
                setBoldForSelectedText()
        }else if (item.itemId==R.id.id_color) {
            if(binding.colorPicker.isShown) {
                closeColorPicker()
            }else{openColorPicker()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setBoldForSelectedText() = with(binding) {
        val startPos = edDeccription.selectionStart
        val endPos = edDeccription.selectionEnd
        val styles = edDeccription.text.getSpans(startPos, endPos, StyleSpan::class.java)
        var boldStyle : StyleSpan? = null
        if (styles.isNotEmpty()){
            edDeccription.text.removeSpan(styles[0])
        }else{
            boldStyle = StyleSpan(Typeface.BOLD)
        }
        edDeccription.text.setSpan(boldStyle,startPos,endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        edDeccription.text.trim()
        edDeccription.setSelection(startPos)
    }

    private fun setColorForSelectedText(colorId : Int) = with(binding) {
        val startPos = edDeccription.selectionStart
        val endPos = edDeccription.selectionEnd
        val styles = edDeccription.text.getSpans(startPos, endPos, ForegroundColorSpan::class.java)
        if (styles.isNotEmpty()){
            edDeccription.text.removeSpan(styles[0])
        }
        edDeccription.text.setSpan(ForegroundColorSpan(ContextCompat.getColor(this@NewNoteActivity, colorId)),
            startPos,endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        edDeccription.text.trim()
        edDeccription.setSelection(startPos)
    }

    private fun setMainResult(){
        var editState = "new"
        val tempNote : NoteItem?
        if (note==null){
            tempNote = createNewNote()
        }else{
            editState = "update"
            tempNote = updateNote()
        }
        val i = Intent().apply {
            putExtra(NoteFragment.NEW_NOTE_KEY, tempNote)
            putExtra(NoteFragment.EDIT_STATE_KEY, editState)
        }
        setResult(RESULT_OK, i)
        finish()
    }

    private fun updateNote(): NoteItem?= with(binding){
       return note?.copy(title = edTitle.text.toString(),
           content =HtmlManager.toHtml(edDeccription.text) )
    }



    private fun createNewNote():NoteItem{
        return NoteItem(null,binding.edTitle.text.toString(),
           HtmlManager.toHtml(binding.edDeccription.text) ,
            TimeManager.getCurrentTime(),"")
    }

    private fun actionBarSettings(){
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    private fun openColorPicker(){
        binding.colorPicker.visibility = View.VISIBLE
        val openAnim = AnimationUtils.loadAnimation(this, R.anim.open_color_picker)
        binding.colorPicker.startAnimation(openAnim)
    }

    private fun closeColorPicker(){

        val openAnim = AnimationUtils.loadAnimation(this, R.anim.close_color_picker)
        openAnim.setAnimationListener(object :Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.colorPicker.visibility = View.GONE

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }

        })
        binding.colorPicker.startAnimation(openAnim)
    }

    private fun actionMenuCallback(){
        val actionCallback = object  : ActionMode.Callback{
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menu?.clear()
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menu?.clear()
                return true
            }

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {

            }

        }
        binding.edDeccription.customSelectionActionModeCallback = actionCallback
    }

    private fun setTextSize() = with(binding){
        edTitle.setTextSize(pref?.getString("title_size_key","16"))
        edDeccription.setTextSize(pref?.getString("content_size_key","14"))
    }

    private fun EditText.setTextSize(size : String?){
        if (size!=null)this.textSize= size.toFloat()

    }

    private fun getSelectedThem():Int {
        return if (defPref.getString("theme_key" , "blue")=="blue"){
            R.style.Theme_NewNoteBlue
        }else{
            R.style.Theme_NewNoteGreen
        }
    }

}