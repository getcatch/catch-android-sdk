package com.getcatch.android.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.getcatch.android.R
import com.getcatch.android.databinding.TofuViewBinding
import com.getcatch.android.util.centsToDollarsString

public class TOFUView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val viewBinding = TofuViewBinding.inflate(
        LayoutInflater.from(context),
        this
    )
    private val tofuTextView = viewBinding.tofuText
    //    private val tofuImageView = viewBinding.tofuImage

    public fun setRedeemableRewards(cents: Int) {
        tofuTextView.text = context.getString(R.string.tofu_redeem_amount, cents.centsToDollarsString())
    }
}
