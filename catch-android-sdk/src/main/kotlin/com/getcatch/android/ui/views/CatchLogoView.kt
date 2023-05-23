package com.getcatch.android.ui.views

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.AbstractComposeView
import com.getcatch.android.R
import com.getcatch.android.ui.CatchLogoSize
import com.getcatch.android.ui.composables.CatchLogo
import com.getcatch.android.ui.theming.CatchColorTheme
import com.getcatch.android.utils.getCatchColorTheme
import com.getcatch.android.utils.getCatchLogoSize

/**
 * A view which displays Catch's logo.
 */
public class CatchLogoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val _size: MutableState<CatchLogoSize> = mutableStateOf(CatchLogoSize.SMALL)
    private val _colorTheme = mutableStateOf<CatchColorTheme?>(null)

    /**
     * The [`CatchLogoSize`](CatchLogoSize) options are meant to provide some recommended defaults
     * while allowing for customization by using the [`CatchLogoSize.FILL`](CatchLogoSize.FILL)
     * within a container of the size of your choosing.
     */
    public var size: CatchLogoSize by _size

    /**
     * The Catch color [`CatchColorTheme`](CatchColorTheme). If no theme is set, the theme set globally on
     * the [`Catch`](com.getcatch.android.Catch) object will be used, which defaults to
     * [`CatchColorTheme.Light`](CatchColorTheme.Light).
     */
    public var colorTheme: CatchColorTheme? by _colorTheme

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CatchLogoView, 0, 0)
            .apply {
                try {
                    getCatchLogoSize()?.let { _size.value = it }
                    _colorTheme.value =
                        getCatchColorTheme(R.styleable.CatchLogoView_colorTheme)
                } finally {
                    recycle()
                }
            }
    }

    @Composable
    override fun Content() {
        CatchLogo(_size.value, _colorTheme.value)
    }
}
