package com.example.shoppinglist.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.shoppinglist.R
import com.example.shoppinglist.fragments.SettingsFragment

class SettingsActivity : AppCompatActivity() {
    private lateinit var defPref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        setTheme(getSelectedThem())
        setContentView(R.layout.activity_settings)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.placeHolder)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        if (savedInstanceState==null){
            supportFragmentManager.beginTransaction().replace(R.id.placeHolder , SettingsFragment()).commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==android.R.id.home)finish()
        return super.onOptionsItemSelected(item)
    }

    private fun getSelectedThem():Int {
        return if (defPref.getString("theme_key" , "blue")=="blue"){
            R.style.Base_Theme_ShoppingListBlue
        }else{
            R.style.Base_Theme_ShoppingListGreen
        }
    }
}