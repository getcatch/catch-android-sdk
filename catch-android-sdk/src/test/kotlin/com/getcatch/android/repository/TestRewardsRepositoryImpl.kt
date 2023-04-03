package com.getcatch.android.repository

import com.getcatch.android.models.CalculatedReward
import com.getcatch.android.models.EarnedRewardsSummary
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.network.clients.transactions.TransactionsSvcClient
import com.getcatch.android.test.helpers.FakeDataProvider
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.fail
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.anyOrNull
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verifyNoInteractions

public class TestRewardsRepositoryImpl {
    private val tenDollars = 1000
    private val oneDollar = 100

    @Test
    public fun `fetchCalculatedEarnedReward, defaults to percentage when price invalid`() {
        val earnedRewards = FakeDataProvider.EarnedRewards.default(0)
        val rewardsRepo = createRewardsRepo(earnedRewards)
        runBlocking {
            val reward = rewardsRepo.fetchCalculatedEarnedReward(
                user = FakeDataProvider.User.Returning,
                price = 0,
                items = null,
                userCohorts = null,
            )
            when (reward) {
                is CalculatedReward.PercentRate -> {
                    assertThat(reward.percent).isEqualTo(FakeDataProvider.merchant.defaultEarnedRewardsRate)
                }
                else -> fail(
                    "Calculate earned rewards should fall back to merchant's default rate due to invalid price"
                )
            }
        }
    }

    @Test
    public fun `fetchCalculatedEarnedReward, signup bonus eligible user returns earned credits`() {
        val earnedRewards = FakeDataProvider.EarnedRewards.newUser(tenDollars)
        val user = FakeDataProvider.User.New
        val rewardsRepo = createRewardsRepo(earnedRewards)
        val totalEarnedRewards = earnedRewards.earnedRewardsTotal ?: 0
        runBlocking {
            val reward = rewardsRepo.fetchCalculatedEarnedReward(
                user = user,
                price = tenDollars,
                items = null,
                userCohorts = null
            )
            when (reward) {
                is CalculatedReward.EarnedCredits -> {
                    assertThat(reward.amount).isEqualTo(totalEarnedRewards)
                }
                else -> fail("Calculate earned rewards should have returned earned credits equal")
            }
        }
    }

    @Test
    public fun `fetchCalculatedEarnedReward, sign up discount experiment returns saved including existing rewards`() {
        val earnedRewards = FakeDataProvider.EarnedRewards.signUpDiscount(
            price = tenDollars,
            signUpDiscount = oneDollar,
        )
        val user = FakeDataProvider.User.newWithCredits()
        val rewardsRepo = createRewardsRepo(earnedRewards)
        val totalAvailableRewards = earnedRewards.signUpDiscountAmount + (user.rewardAmount ?: 0)
        runBlocking {
            val reward = rewardsRepo.fetchCalculatedEarnedReward(
                user = user,
                price = tenDollars,
                items = null,
                userCohorts = null
            )
            when (reward) {
                is CalculatedReward.Saved -> {
                    // Since the price is less than the rewards available, we should see the
                    assertThat(reward.amount).isEqualTo(totalAvailableRewards)
                }
                else -> fail("Calculate earned rewards should have returned earned credits equal to price")
            }
        }
    }

    @Test
    public fun `fetchCalculatedEarnedReward, sign up discount experiment price less than discount`() {
        val earnedRewards = FakeDataProvider.EarnedRewards.signUpDiscount(
            price = tenDollars,
            signUpDiscount = tenDollars + 1
        )
        val user = FakeDataProvider.User.New
        val rewardsRepo = createRewardsRepo(earnedRewards)
        runBlocking {
            val reward = rewardsRepo.fetchCalculatedEarnedReward(
                user = user,
                price = tenDollars,
                items = null,
                userCohorts = null
            )
            when (reward) {
                is CalculatedReward.Saved -> {
                    // Since the price is less than the rewards available, we should see the price as the savings
                    assertThat(reward.amount).isEqualTo(tenDollars)
                }
                else -> fail("Calculate earned rewards should have returned earned credits equal to price")
            }
        }
    }

    @Test
    public fun `fetchCalculatedEarnedReward, no credits and no rewards shows best reward rate`() {
        // Make reward rate double the merchant's default (could simulate configurable rules)
        val earnedRewards = FakeDataProvider.EarnedRewards.noRewards(
            rewardRate = FakeDataProvider.merchant.defaultEarnedRewardsRate * 2
        )
        val user = FakeDataProvider.User.NoCredits
        val rewardsRepo = createRewardsRepo(earnedRewards)
        runBlocking {
            val reward = rewardsRepo.fetchCalculatedEarnedReward(
                user = user,
                price = tenDollars,
                items = null,
                userCohorts = null
            )
            when (reward) {
                is CalculatedReward.PercentRate -> {
                    // Since the price is less than the rewards available, we should see the price as the savings
                    assertThat(reward.percent).isEqualTo(earnedRewards.percentageRewardRate)
                }
                else -> fail("Calculate earned rewards should have returned the max percentage rate")
            }
        }
    }

    @Test
    public fun `fetchEarnedRewardSummary, configurable rewards disabled no network request`() {
        val merchant = FakeDataProvider.merchant.copy(enableConfigurableRewards = false)
        val mockMerchantRepo = mock<MerchantRepository>()
        val mockTxnSvcClient = mock<TransactionsSvcClient>()
        val rewardsRepo = RewardsRepositoryImpl(mockMerchantRepo, mockTxnSvcClient)
        runBlocking {
            rewardsRepo.fetchEarnedRewardSummary(
                price = tenDollars,
                items = null,
                userCohorts = null,
                merchant = merchant,
                user = FakeDataProvider.user,
            )
            verifyNoInteractions(mockTxnSvcClient)
        }
    }

    private fun createRewardsRepo(
        earnedRewards: EarnedRewardsSummary,
    ): RewardsRepositoryImpl {
        val mockMerchantRepo = mock<MerchantRepository> {
            on { activeMerchant } doReturn MutableStateFlow(FakeDataProvider.merchant).asStateFlow()
        }
        val mockTxnSvcClient = mock<TransactionsSvcClient> {
            onBlocking {
                calculateEarnedRewards(
                    merchantId = any(),
                    amountEligibleForEarnedRewards = any(),
                    isNewCatchUser = any(),
                    items = anyOrNull(),
                    userCohorts = anyOrNull(),
                    excludeCartBasedRules = any(),
                    includeEarnedRewardBreakdown = any()
                )
            } doReturn NetworkResponse.Success(earnedRewards)
        }
        return RewardsRepositoryImpl(mockMerchantRepo, mockTxnSvcClient)
    }
}
