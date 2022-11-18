package com.getcatch.android.theming.atomization.widgets

import com.getcatch.android.theming.ThemeConfig
import com.getcatch.android.theming.atomization.CatchAtoms
import com.getcatch.android.theming.atomization.atoms.ActionButtonAtom
import com.getcatch.android.theming.atomization.atoms.BenefitTextAtom
import com.getcatch.android.theming.atomization.atoms.BorderAtom
import com.getcatch.android.theming.atomization.atoms.TextAtom

public data class PurchaseConfirmationAtoms(
    val text: TextAtom? = null,
    val border: BorderAtom? = null,
    val benefitText: BenefitTextAtom? = null,
    val actionButton: ActionButtonAtom? = null,
) {
    internal data class Resolved(
        val text: TextAtom.Resolved,
        val border: BorderAtom.Resolved,
        val benefitText: BenefitTextAtom.Resolved,
        val actionButton: ActionButtonAtom.Resolved,
    ) {
        fun withOverrides(overrides: PurchaseConfirmationAtoms?): Resolved {
            if (overrides == null) return this
            return Resolved(
                text = text.withOverrides(overrides.text),
                border = border.withOverrides(overrides.border),
                benefitText = benefitText.withOverrides(overrides.benefitText),
                actionButton = actionButton.withOverrides(overrides.actionButton),
            )
        }

        fun withOverrides(overrides: CatchAtoms) = Resolved(
            text = text.withOverrides(overrides.purchaseConfirmation?.text ?: overrides.text),
            border = border.withOverrides(
                overrides.purchaseConfirmation?.border ?: overrides.border
            ),
            benefitText = benefitText.withOverrides(
                overrides.purchaseConfirmation?.benefitText ?: overrides.benefitText
            ),
            actionButton = actionButton.withOverrides(
                overrides.purchaseConfirmation?.actionButton ?: overrides.actionButton
            ),
        )
    }

    internal companion object {
        fun defaults(
            textAtom: TextAtom.Resolved,
            borderAtom: BorderAtom.Resolved,
            benefitTextAtom: BenefitTextAtom.Resolved,
            actionButtonAtom: ActionButtonAtom.Resolved,
        ) = Resolved(
            text = textAtom,
            border = borderAtom,
            benefitText = benefitTextAtom,
            actionButton = actionButtonAtom,
        )

        fun defaults(theme: ThemeConfig) = Resolved(
            text = TextAtom.defaults(theme),
            border = BorderAtom.defaults(theme),
            benefitText = BenefitTextAtom.defaults(theme),
            actionButton = ActionButtonAtom.defaults(theme),
        )
    }
}
