package com.getcatch.sample

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.getcatch.android.composable.TOFUWidget
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

@AndroidEntryPoint
class ComposeActivity : ComponentActivity() {

    private val vm: MainViewModel by viewModels()

    private fun goToViewBasedActivity() {
        val intent =
            Intent(this, ViewBasedActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            }
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var redeemableRewards by remember { mutableStateOf<Int?>(null) }
            val secondsPassed by vm.secondsFlow.collectAsState()
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Seconds passed: $secondsPassed")
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .testTag("TOFUWidgetWrapper")
                        .clickable {
                            redeemableRewards = Random.nextInt(from = 0, until = 10000)
                        }
                ) {
                    TOFUWidget(redeemableRewards)
                }
                Button(onClick = { goToViewBasedActivity() }) {
                    Text(text = stringResource(id = R.string.go_to_view_based_activity_btn_label))
                }
            }
        }
    }
}
