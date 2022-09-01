package com.getcatch.sample

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.getcatch.android.view.ComposeBackedTOFUView
import com.getcatch.android.view.TOFUView
import com.getcatch.sample.databinding.ActivityViewBasedBinding
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class ViewBasedActivity : AppCompatActivity() {

    private val vm: MainViewModel by viewModels()

    private lateinit var binding: ActivityViewBasedBinding
    private lateinit var tofuView: TOFUView
    private lateinit var composeBackedTOFUView: ComposeBackedTOFUView
    private lateinit var goToComposeActivityBtn: MaterialButton

    private fun goToComposeActivity() {
        val intent = Intent(this, ComposeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_based)
        goToComposeActivityBtn = binding.goToComposeActivityBtn
        goToComposeActivityBtn.setOnClickListener {
            goToComposeActivity()
        }
        tofuView = binding.testTofuView
        tofuView.setOnClickListener {
            tofuView.setRedeemableRewards(Random.nextInt(from = 0, until = 10000))
        }
        composeBackedTOFUView = binding.testComposeTofuView
        composeBackedTOFUView.setOnClickListener {
            composeBackedTOFUView.redeemableRewards = Random.nextInt(from = 0, until = 10000)
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.secondsFlow.collectLatest {
                    binding.textView.text = it.toString()
                }
            }
        }
    }
}
