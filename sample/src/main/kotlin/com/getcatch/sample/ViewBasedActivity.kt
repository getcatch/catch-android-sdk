package com.getcatch.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.getcatch.android.views.CalloutView
import com.getcatch.sample.databinding.ActivityViewBasedBinding
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class ViewBasedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewBasedBinding
    private lateinit var calloutView: CalloutView
    private lateinit var orPrefixCalloutView: CalloutView
    private lateinit var goToComposeActivityBtn: MaterialButton
    private lateinit var toggleThemeBtn: MaterialButton

    private fun goToComposeActivity() {
        val intent = Intent(this, ComposeActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_based)
        goToComposeActivityBtn = binding.goToComposeActivityBtn
        goToComposeActivityBtn.setOnClickListener {
            goToComposeActivity()
        }
        calloutView = binding.testCalloutView
        calloutView.setOnClickListener {
            if (calloutView.price == null) {
                calloutView.price = Random.nextInt(from = 0, until = 10000)
            } else {
                calloutView.price = null
            }
        }
        orPrefixCalloutView = binding.testOrPrefixCalloutView
        orPrefixCalloutView.setOnClickListener  {
            if (orPrefixCalloutView.price == null) {
                orPrefixCalloutView.price = Random.nextInt(from = 0, until = 10000)
            } else {
                orPrefixCalloutView.price = null
            }
        }
        toggleThemeBtn = binding.toggleThemeBtn
        toggleThemeBtn.setOnClickListener {
            val newMode = when (AppCompatDelegate.getDefaultNightMode()) {
                AppCompatDelegate.MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_YES
                else -> AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(newMode)
        }
    }
}
