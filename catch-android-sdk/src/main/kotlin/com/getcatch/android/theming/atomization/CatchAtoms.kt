package com.getcatch.android.theming.atomization

import com.getcatch.android.theming.ThemeConfig
import com.getcatch.android.theming.atomization.atoms.ActionButtonAtom
import com.getcatch.android.theming.atomization.atoms.BenefitTextAtom
import com.getcatch.android.theming.atomization.atoms.BorderAtom
import com.getcatch.android.theming.atomization.atoms.TextAtom
import com.getcatch.android.theming.atomization.widgets.CalloutAtoms
import com.getcatch.android.theming.atomization.widgets.CampaignLinkAtoms
import com.getcatch.android.theming.atomization.widgets.ExpressCheckoutCalloutAtoms
import com.getcatch.android.theming.atomization.widgets.PaymentMethodAtoms
import com.getcatch.android.theming.atomization.widgets.PurchaseConfirmationAtoms

/**
 * The global set of atoms that can be configured to customize styles of elements at any level.
 */
public data class CatchAtoms(
    val text: TextAtom? = null,
    val border: BorderAtom? = null,
    val benefitText: BenefitTextAtom? = null,
    val actionButton: ActionButtonAtom? = null,
    val callout: CalloutAtoms? = null,
    val campaignLink: CampaignLinkAtoms? = null,
    val expressCheckout: ExpressCheckoutCalloutAtoms? = null,
    val paymentMethod: PaymentMethodAtoms? = null,
    val purchaseConfirmation: PurchaseConfirmationAtoms? = null,
) {
    /**
     * An internal class modeling all of the resolved values for each atom once cascading has happened.
     */
    internal data class Resolved(
        val text: TextAtom.Resolved,
        val border: BorderAtom.Resolved,
        val benefitText: BenefitTextAtom.Resolved,
        val actionButton: ActionButtonAtom.Resolved,
        val callout: CalloutAtoms.Resolved,
        val campaignLink: CampaignLinkAtoms.Resolved,
        val expressCheckout: ExpressCheckoutCalloutAtoms.Resolved,
        val paymentMethod: PaymentMethodAtoms.Resolved,
        val purchaseConfirmation: PurchaseConfirmationAtoms.Resolved,
    ) {

        internal fun withOverrides(overrides: CatchAtoms?): Resolved {
            if (overrides == null) return this
            return Resolved(
                text = text.withOverrides(overrides.text),
                border = border.withOverrides(overrides.border),
                benefitText = benefitText.withOverrides(overrides.benefitText),
                actionButton = actionButton.withOverrides(overrides.actionButton),
                callout = callout.withOverrides(overrides),
                campaignLink = campaignLink.withOverrides(overrides),
                expressCheckout = expressCheckout.withOverrides(overrides),
                paymentMethod = paymentMethod.withOverrides(overrides),
                purchaseConfirmation = purchaseConfirmation.withOverrides(overrides),
            )
        }
    }

    internal companion object {
        internal fun defaults(theme: ThemeConfig): Resolved {
            val textDefaults = TextAtom.defaults(theme)
            val borderDefaults = BorderAtom.defaults(theme)
            val benefitTextDefaults = BenefitTextAtom.defaults(theme)
            val actionButtonDefaults = ActionButtonAtom.defaults(theme)
            val calloutDefaults = CalloutAtoms.defaults(
                textAtom = textDefaults,
                borderAtom = borderDefaults,
                benefitTextAtom = benefitTextDefaults,
            )
            val campaignLinkDefaults = CampaignLinkAtoms.defaults(
                textAtom = textDefaults,
                borderAtom = borderDefaults,
                benefitTextAtom = benefitTextDefaults,
                actionButtonAtom = actionButtonDefaults,
            )
            val expressCheckoutDefaults = ExpressCheckoutCalloutAtoms.defaults(
                textAtom = textDefaults,
                borderAtom = borderDefaults,
                benefitTextAtom = benefitTextDefaults,
            )
            val paymentMethodDefaults = PaymentMethodAtoms.defaults(
                textAtom = textDefaults,
                borderAtom = borderDefaults,
                benefitTextAtom = benefitTextDefaults,
            )
            val purchaseConfirmationDefaults = PurchaseConfirmationAtoms.defaults(
                textAtom = textDefaults,
                borderAtom = borderDefaults,
                benefitTextAtom = benefitTextDefaults,
                actionButtonAtom = actionButtonDefaults,
            )
            return Resolved(
                text = textDefaults,
                border = borderDefaults,
                benefitText = benefitTextDefaults,
                actionButton = actionButtonDefaults,
                callout = calloutDefaults,
                campaignLink = campaignLinkDefaults,
                expressCheckout = expressCheckoutDefaults,
                paymentMethod = paymentMethodDefaults,
                purchaseConfirmation = purchaseConfirmationDefaults,
            )
        }
    }
}
