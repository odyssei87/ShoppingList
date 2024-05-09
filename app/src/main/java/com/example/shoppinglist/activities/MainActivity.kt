package com.example.shoppinglist.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.example.shoppinglist.R
import com.example.shoppinglist.databinding.ActivityMainBinding
import com.example.shoppinglist.dialogs.NewListDialog
import com.example.shoppinglist.fragments.FragmentManager
import com.example.shoppinglist.fragments.NoteFragment
import com.example.shoppinglist.fragments.ShopListNamesFragment
import com.example.shoppinglist.settings.SettingsActivity

class MainActivity : AppCompatActivity() , NewListDialog.Listener{
    lateinit var binding:ActivityMainBinding
    private var currentMenuItemId= R.id.shop_list
    private var currentTheme= ""
    private lateinit var defPref : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        defPref = PreferenceManager.getDefaultSharedPreferences(this)
        currentTheme=defPref.getString("theme_key", "blue").toString()
        setTheme(getSelectedThem())
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
        setBottomNavListener()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
    }
    private fun setBottomNavListener(){
        binding.bNaw.setOnItemSelectedListener {
            when(it.itemId){
                R.id.settings->{
                    startActivity(Intent(this, SettingsActivity::class.java))
                }
                R.id.notes->{
                    FragmentManager.setFragment(NoteFragment.newInstance(), this)
                    currentMenuItemId= R.id.notes
                }
                R.id.shop_list->{
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
                    currentMenuItemId= R.id.shop_list
                }
                R.id.new_item->{
                    FragmentManager.currentFrag?.onClickNew()
                }
            }
            true
        }
    }

    override fun onResume() {
        super.onResume()
        binding.bNaw.selectedItemId = currentMenuItemId
        if (defPref.getString("theme_key", "blue")!=currentTheme)recreate()

    }

    private fun getSelectedThem():Int {
        return if (defPref.getString("theme_key" , "blue")=="blue"){
            R.style.Base_Theme_ShoppingListBlue
        }else{
            R.style.Base_Theme_ShoppingListGreen
        }
    }

    override fun onClick(name: String) {
        Log.d("MyLog","Name : $name")
    }
}