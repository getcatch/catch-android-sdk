package com.getcatch.android.views

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.AbstractComposeView
import com.getcatch.android.R
import com.getcatch.android.composables.Callout
import com.getcatch.android.theming.CalloutBorderStyle

public class CalloutView @JvmOverloads constructor(
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
    private val hasOrPrefix: Boolean
    private val borderStyle: CalloutBorderStyle?

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CalloutView, 0, 0).apply {
            try {
                hasOrPrefix = getBoolean(R.styleable.CalloutView_hasOrPrefix, false)
                borderStyle = if (hasValue(R.styleable.CalloutView_borderStyle)) {
                    // The mapping of these values is delicate as it is simply specified in
                    // `res/values/attrs.xml`. Ideally, some sort of codegen would link the attrs
                    // int to the actual sealed class, but that is probably more work than it is
                    // worth for a value that is unlikely to change.
                    when (getInt(R.styleable.CalloutView_borderStyle, -1)) {
                        0 -> CalloutBorderStyle.Square
                        1 -> CalloutBorderStyle.SlightRound
                        2 -> CalloutBorderStyle.Pill
                        else -> null
                    }
                } else {
                    null
                }
            } finally {
                recycle()
            }
        }
    }

    @Composable
    override fun Content() {
        Callout(
            hasOrPrefix = hasOrPrefix,
            borderStyle = borderStyle
        )
    }
}
