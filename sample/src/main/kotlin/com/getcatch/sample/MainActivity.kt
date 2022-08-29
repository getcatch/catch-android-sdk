package com.getcatch.sample

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.getcatch.android.view.TOFUView
import com.getcatch.sample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val vm: MainViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private lateinit var tofuView: TOFUView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        tofuView = binding.testTofuView
        tofuView.setOnClickListener {
            tofuView.setRedeemableRewards(Random.nextInt(from = 0, until = 10000))
        }

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.complexFLow.collectLatest {
                    binding.textView.text = it.toString()
                }
            }
        }
    }
}
