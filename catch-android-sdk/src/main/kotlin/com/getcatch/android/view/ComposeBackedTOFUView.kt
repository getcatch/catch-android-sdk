package com.getcatch.android.view

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AbstractComposeView
import com.getcatch.android.composable.TOFUWidget

public class ComposeBackedTOFUView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {

    // Public getter/setter for our title that delegates to the State value.
    public var redeemableRewards: Int?
        get() = redeemableRewardsCents.value
        set(value) {
            redeemableRewardsCents.value = value
        }

    private val redeemableRewardsCents = mutableStateOf<Int?>(null)

    @Composable
    override fun Content() {
        TOFUWidget(redeemableRewards = redeemableRewardsCents.value)
    }
}
