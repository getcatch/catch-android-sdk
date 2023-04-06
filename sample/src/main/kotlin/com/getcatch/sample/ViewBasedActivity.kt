package com.getcatch.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.getcatch.android.ui.views.CalloutView
import com.getcatch.android.ui.views.ExpressCheckoutCalloutView
import com.getcatch.android.ui.views.PaymentMethodView
import com.getcatch.sample.databinding.ActivityViewBasedBinding
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class ViewBasedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewBasedBinding
    private lateinit var basicCallout: CalloutView
    private lateinit var customCallout: CalloutView
    private lateinit var basicPaymentMethod: PaymentMethodView
    private lateinit var customPaymentMethod: PaymentMethodView
    private lateinit var basicExpressCheckoutCallout: ExpressCheckoutCalloutView
    private lateinit var customExpressCheckoutCallout: ExpressCheckoutCalloutView
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
        basicCallout = binding.basicCallout
        basicCallout.setOnClickListener {
            if (basicCallout.price == null) {
                basicCallout.price = Random.nextInt(from = 0, until = 10000)
            } else {
                basicCallout.price = null
            }
        }
        customCallout = binding.customCallout
        customCallout.setOnClickListener  {
            if (customCallout.price == null) {
                customCallout.price = Random.nextInt(from = 0, until = 10000)
            } else {
                customCallout.price = null
            }
        }
        basicPaymentMethod = binding.basicPaymentMethod
        customPaymentMethod = binding.customPaymentMethod
        basicExpressCheckoutCallout = binding.basicExpressCheckoutCallout
        customExpressCheckoutCallout = binding.customExpressCheckoutCallout
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
