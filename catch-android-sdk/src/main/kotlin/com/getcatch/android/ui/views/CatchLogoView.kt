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
import com.getcatch.android.ui.theming.ThemeVariant
import com.getcatch.android.utils.getCatchLogoSize
import com.getcatch.android.utils.getThemeVariant

/**
 * A view which displays Catch's logo.
 */
public class CatchLogoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    private val _size: MutableState<CatchLogoSize> = mutableStateOf(CatchLogoSize.SMALL)
    private val _themeVariant = mutableStateOf<ThemeVariant?>(null)

    /**
     * The [CatchLogoSize] options are meant to provide some recommended defaults while
     * allowing for customization by using the [CatchLogoSize.FILL] within a container of the size of
     * your choosing.
     */
    public var size: CatchLogoSize by _size

    /**
     * The Catch color [ThemeVariant]. If no theme is set, the theme set globally on the
     * [Catch] object will be used, which defaults to [ThemeVariant.Light].
     */
    public var themeVariant: ThemeVariant? by _themeVariant

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.CatchLogoView, 0, 0)
            .apply {
                try {
                    getCatchLogoSize()?.let { _size.value = it }
                    _themeVariant.value = getThemeVariant(R.styleable.CatchLogoView_themeVariant)
                } finally {
                    recycle()
                }
            }
    }

    @Composable
    override fun Content() {
        CatchLogo(_size.value)
    }
}
