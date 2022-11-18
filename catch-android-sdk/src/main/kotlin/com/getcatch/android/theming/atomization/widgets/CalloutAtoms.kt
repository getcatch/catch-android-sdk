package com.getcatch.android.theming.atomization.widgets

import com.getcatch.android.theming.ThemeConfig
import com.getcatch.android.theming.atomization.CatchAtoms
import com.getcatch.android.theming.atomization.atoms.BenefitTextAtom
import com.getcatch.android.theming.atomization.atoms.BorderAtom
import com.getcatch.android.theming.atomization.atoms.TextAtom

public data class CalloutAtoms(
    val text: TextAtom? = null,
    val border: BorderAtom? = null,
    val benefitText: BenefitTextAtom? = null,
) {
    internal data class Resolved(
        val text: TextAtom.Resolved,
        val border: BorderAtom.Resolved,
        val benefitText: BenefitTextAtom.Resolved,
    ) {
        fun withOverrides(overrides: CalloutAtoms?): Resolved {
            if (overrides == null) return this
            return Resolved(
                text = text.withOverrides(overrides.text),
                border = border.withOverrides(overrides.border),
                benefitText = benefitText.withOverrides(overrides.benefitText),
            )
        }

        fun withOverrides(overrides: CatchAtoms) = Resolved(
            text = text.withOverrides(overrides.callout?.text ?: overrides.text),
            border = border.withOverrides(overrides.callout?.border ?: overrides.border),
            benefitText = benefitText.withOverrides(
                overrides.callout?.benefitText ?: overrides.benefitText
            ),
        )
    }

    internal companion object {
        fun defaults(
            textAtom: TextAtom.Resolved,
            borderAtom: BorderAtom.Resolved,
            benefitTextAtom: BenefitTextAtom.Resolved,
        ) = Resolved(
            text = textAtom,
            border = borderAtom,
            benefitText = benefitTextAtom,
        )

        fun defaults(theme: ThemeConfig) = Resolved(
            text = TextAtom.defaults(theme),
            border = BorderAtom.defaults(theme),
            benefitText = BenefitTextAtom.defaults(theme),
        )
    }
}
