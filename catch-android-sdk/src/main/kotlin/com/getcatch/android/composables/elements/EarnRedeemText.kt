package com.getcatch.android.composables.elements

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.getcatch.android.theming.CatchTheme
import com.getcatch.android.theming.CatchTypography
import com.getcatch.android.theming.LocalColors
import com.getcatch.android.theming.atomization.atoms.BenefitTextAtom
import com.getcatch.android.utils.centsToDollarsString
import com.getcatch.android.utils.toPercentString
import kotlin.math.min

@Composable
internal fun EarnRedeemText(
    price: Int? = 0,
    capitalize: Boolean = true,
    prefixComposable: @Composable (() -> Unit)? = null,
    suffixComposable: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = CatchTypography.CatchTextStyles.bodySmall,
    benefitTextAtom: BenefitTextAtom.Resolved? = null,
) {
    val atoms = benefitTextAtom ?: CatchTheme.atoms.benefitText
    val earnRedeemMessage =
        generateEarnRedeemMessage(price = price ?: 0, capitalize = capitalize)
    val loading = remember { mutableStateOf(false) }
    if (loading.value) {
        LoadingBar(
            modifier = Modifier
                .width(115.dp)
                .height(12.dp)
        )
    } else {
        prefixComposable?.invoke()
        val color = when (earnRedeemMessage.type) {
            EarnRedeemType.Earn -> atoms.earnColor
            EarnRedeemType.Redeem -> atoms.redeemColor
        }
        Text(
            text = earnRedeemMessage.message,
            color = color,
            style = textStyle,
            fontWeight = atoms.fontWeight.toComposeFontWeight(),
            textDecoration = TextDecoration.Underline
        )
        suffixComposable?.invoke()
    }
}

@Composable
internal fun EarnRedeemText(
    price: Int? = 0,
    capitalize: Boolean = true,
    prefix: String? = null,
    suffix: String? = null,
    textStyle: TextStyle = CatchTypography.CatchTextStyles.bodySmall,
    benefitTextAtom: BenefitTextAtom.Resolved? = null,
) = EarnRedeemText(
    price = price,
    capitalize = capitalize,
    prefixComposable = prefix?.let {
        @Composable {
            Text(
                text = it,
                style = textStyle,
            )
            Spacer(modifier = Modifier.width(3.dp))
        }
    },
    suffixComposable = suffix?.let {
        @Composable {
            Spacer(modifier = Modifier.width(3.dp))
            Text(
                text = it,
                style = textStyle,
            )
        }
    },
    textStyle = textStyle,
    benefitTextAtom,
)

internal enum class EarnRedeemMessageVariant(val value: String) {
    DEFAULT(" credit"),
    EXPRESS("");
}

internal enum class CurrencyCode(val symbol: String) {
    USD(symbol = "$");
}

internal sealed interface EarnRedeemType {
    object Earn : EarnRedeemType
    object Redeem : EarnRedeemType
}

internal data class EarnRedeemMessage(val message: String, val type: EarnRedeemType) {
    fun capitalize() = EarnRedeemMessage(
        message = message.replaceFirstChar { it.uppercase() },
        type = type
    )

    val color: Color
        @Composable
        get() = when (type) {
            EarnRedeemType.Earn -> LocalColors.current.funTextEarning
            EarnRedeemType.Redeem -> LocalColors.current.funTextEarning
        }
}

internal fun generateEarnRedeemMessage(
    price: Int = 0,
    capitalize: Boolean = true,
    rewardsRatePercent: Float = 0.1f,
    userRewardAmount: Int = 0,
    calculatedEarnedRewardAmount: Int = 0,
    signUpDiscount: Int = 0,
    variant: EarnRedeemMessageVariant = EarnRedeemMessageVariant.DEFAULT,
//    currency: CurrencyCode = CurrencyCode.USD,
): EarnRedeemMessage {
    val savedAmount = signUpDiscount + userRewardAmount
    val message: EarnRedeemMessage = when {
        signUpDiscount > 0 -> {
            val displayedAmount = if (price > 0) {
                min(savedAmount, price)
            } else {
                signUpDiscount
            }
            EarnRedeemMessage(
                message = "save ${displayedAmount.centsToDollarsString()}",
                type = EarnRedeemType.Earn
            )
        }
        // If the price is not set, or it's not a positive amount, we fall back to a generic "earn % credit" message.
        price <= 0 -> getGenericPercentageMessaging(rewardsRatePercent, variant)
        // If the user has rewards to redeem, use that instead of the earned amount
        userRewardAmount > 0 -> EarnRedeemMessage(
            message = "redeem ${
            min(
                price,
                userRewardAmount
            ).centsToDollarsString()
            }",
            type = EarnRedeemType.Redeem
        )
        // If the user is neither redeeming rewards nor earning rewards, something has gone wrong with the calculate
        // call and we should fall back to a generic "earn % credit" message.
        calculatedEarnedRewardAmount <= 0 ->
            getGenericPercentageMessaging(
                rewardsRatePercent,
                variant
            )
        else -> EarnRedeemMessage(
            message =
            "earn ${calculatedEarnedRewardAmount.centsToDollarsString()}${variant.value}",
            type = EarnRedeemType.Earn
        )
    }
    if (capitalize) {
        return message.capitalize()
    }
    return message
}

internal fun getGenericPercentageMessaging(
    rewardsRatePercent: Float,
    variant: EarnRedeemMessageVariant
) =
    EarnRedeemMessage(
        message = "earn ${rewardsRatePercent.toPercentString()}%${variant.value}",
        type = EarnRedeemType.Earn
    )
