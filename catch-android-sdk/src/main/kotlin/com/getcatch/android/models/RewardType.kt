package com.getcatch.android.models

/**
 * Reward types used to generate default merchant rewards summaries.
 */
internal object RewardType {
    const val UNRESTRICTED_REWARD_RULE_USER_FACING_NAME = "on every order"
    const val UNRESTRICTED_RULE_ENGINE_TYPE = "unrestricted"
    const val NEW_CATCH_USER_REWARD_RULE_USER_FACING_NAME = "First order boost"
    const val NEW_CATCH_USER_RULE_ENGINE_TYPE = "new_catch_user"
    const val PERCENTAGE_REWARD_AMOUNT_TYPE = "percentage"
    const val FLAT_REWARD_AMOUNT_TYPE = "flat"
}
