package com.example.shoppinglist.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.shoppinglist.R

class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.serings_preferense , rootKey)
    }

}