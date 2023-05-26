package com.getcatch.android.network.clients

import com.getcatch.android.models.PublicKey
import com.getcatch.android.network.Environment
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.network.clients.transactions.TransactionsSvcClientImpl
import com.getcatch.android.test.helpers.FakeDataProvider
import com.getcatch.android.test.helpers.ResourceHelpers
import com.getcatch.android.test.mocks.MockHttpClient
import com.getcatch.android.utils.buildUrl
import com.getcatch.android.utils.parameter
import com.google.common.truth.Truth.assertThat
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import kotlin.test.fail

public class TestTransactionsSvcClientImpl {
    private val mockClient = MockHttpClient()
    private val testPublicKey = PublicKey("TEST_PUBLIC_KEY")
    private val transactionsSvcClient = TransactionsSvcClientImpl(
        mockClient.client,
        testPublicKey,
        Environment.LIVE,
    )

    private val testDeviceId = "TEST_DEVICE_ID"
    private val testMerchantId = "TEST_MERCHANT_ID"

    @Before
    public fun setup() {
        mockClient.reset()
    }

    @Test
    public fun `fetchUserData, success`() {
        val responseJson = ResourceHelpers.loadResource("get-public-user-data-response.json")

        mockClient.addResponse(
            method = HttpMethod.Get,
            url = "${transactionsSvcClient.baseUrl}/user_devices/$testDeviceId/user_data?merchant_id=$testMerchantId",
            responseBody = responseJson,
        )

        runBlocking {
            val response = transactionsSvcClient.fetchUserData(testDeviceId, testMerchantId)
            when (response) {
                is NetworkResponse.Success -> assertThat(response.body.userFirstName)
                    .isEqualTo("Luigi")

                is NetworkResponse.Failure -> fail(
                    "Request with valid device and merchant id should succeed and return user data"
                )
            }
        }
    }

    @Test
    public fun `fetchUserData, malformed response body`() {
        val responseJson = "not json"

        mockClient.addResponse(
            method = HttpMethod.Get,
            url = "${transactionsSvcClient.baseUrl}/user_devices/$testDeviceId/user_data?merchant_id=$testMerchantId",
            responseBody = responseJson,
        )

        runBlocking {
            val response = transactionsSvcClient.fetchUserData(testDeviceId, testMerchantId)
            when (response) {
                is NetworkResponse.Success -> fail("Response with improper body should fail JSON conversion")
                is NetworkResponse.Failure -> assertThat(response.error).isInstanceOf(
                    JsonConvertException::class.java
                )
            }
        }
    }

    @Test
    public fun `fetchUserData, non 200 status code`() {
        val responseJson = "{\"message\":\"Something went wrong\"}"

        mockClient.addResponse(
            method = HttpMethod.Get,
            url = "${transactionsSvcClient.baseUrl}/user_devices/$testDeviceId/user_data?merchant_id=$testMerchantId",
            responseBody = responseJson,
            status = HttpStatusCode.BadRequest,
        )

        runBlocking {
            val response = transactionsSvcClient.fetchUserData(testDeviceId, testMerchantId)
            when (response) {
                is NetworkResponse.Success -> fail("Response with non 200 status code should return a Failure")
                is NetworkResponse.Failure -> assertThat(response.error).isNull()
            }
        }
    }

    @Test
    public fun `calculateEarnedRewards, success with full query`() {
        val responseJson =
            ResourceHelpers.loadResource("calculate-earned-rewards-with-breakdown-response.json")

        val items = listOf(FakeDataProvider.randomItem(), FakeDataProvider.randomItem())
        val cohorts = listOf("cohort1", "cohort2")

        val expectedUrl =
            buildUrl("${transactionsSvcClient.baseUrl}/merchants/$testMerchantId/calculate_earned_rewards/public") {
                parameter("amount_eligible_for_earned_rewards", 1000)
                parameter("is_new_catch_user", true)
                parameter("exclude_cart_based_rules", false)
                parameter("include_earned_reward_breakdown", true)
                items.forEach { parameter("items", it.toQueryString()) }
                cohorts.forEach { parameter("user_cohorts", it) }
            }

        mockClient.addResponse(
            method = HttpMethod.Get,
            url = expectedUrl,
            responseBody = responseJson,
        )

        runBlocking {
            val response = transactionsSvcClient.calculateEarnedRewards(
                merchantId = testMerchantId,
                amountEligibleForEarnedRewards = 1000,
                isNewCatchUser = true,
                items = items,
                userCohorts = cohorts,
                excludeCartBasedRules = false,
                includeEarnedRewardBreakdown = true
            )
            when (response) {
                is NetworkResponse.Success -> {
                    assertThat(response.body.percentageRewardRate).isEqualTo(0.15)
                    assertThat(response.body.signUpBonusAmount).isEqualTo(1000)
                    assertThat(response.body.earnedRewardsTotal).isEqualTo(1150)
                }

                is NetworkResponse.Failure -> fail(
                    "Valid request should succeed and return calculated earned rewards"
                )
            }
        }
    }

    @Test
    public fun `calculateEarnedRewards, success with minimal query`() {
        val responseJson =
            ResourceHelpers.loadResource("calculate-earned-rewards-no-breakdown-response.json")

        val expectedUrl =
            buildUrl("${transactionsSvcClient.baseUrl}/merchants/$testMerchantId/calculate_earned_rewards/public") {
                parameter("amount_eligible_for_earned_rewards", 1000)
                parameter("is_new_catch_user", false)
                parameter("exclude_cart_based_rules", true)
                parameter("include_earned_reward_breakdown", false)
            }

        mockClient.addResponse(
            method = HttpMethod.Get,
            url = expectedUrl,
            responseBody = responseJson,
        )

        runBlocking {
            val response = transactionsSvcClient.calculateEarnedRewards(
                merchantId = testMerchantId,
                amountEligibleForEarnedRewards = 1000,
                isNewCatchUser = false,
                items = null,
                userCohorts = null,
                excludeCartBasedRules = true,
                includeEarnedRewardBreakdown = false
            )
            when (response) {
                is NetworkResponse.Success -> {
                    assertThat(response.body.percentageRewardRate).isEqualTo(0.15)
                    assertThat(response.body.signUpBonusAmount).isEqualTo(0)
                    assertThat(response.body.earnedRewardsTotal).isEqualTo(150)
                }

                is NetworkResponse.Failure -> fail(
                    "Valid request should succeed and return calculated earned rewards"
                )
            }
        }
    }

    @Test
    public fun `fetchRewardCampaign, success`() {
        val responseJson =
            ResourceHelpers.loadResource("get-reward-campaign-by-external-name-response.json")
        val testCampaignName = "TEST_CAMPAIGN_NAME"

        mockClient.addResponse(
            method = HttpMethod.Get,
            url = transactionsSvcClient.baseUrl +
                "/merchants/${testPublicKey.value}/reward_campaigns/$testCampaignName/public",
            responseBody = responseJson,
        )

        runBlocking {
            val response = transactionsSvcClient.fetchRewardCampaign(
                campaignName = testCampaignName,
            )
            when (response) {
                is NetworkResponse.Success -> {
                    assertThat(response.body.rewardCampaignId).isEqualTo("rc-dMetos")
                    assertThat(response.body.totalAmount).isEqualTo(1000)
                }

                is NetworkResponse.Failure -> fail(
                    "Valid request should succeed and return reward campaign"
                )
            }
        }
    }

    @Test
    public fun `fetchRewardsForConfirmedCheckout, success`() {
        val responseJson =
            ResourceHelpers.loadResource("rewards-for-confirmed-checkout-response.json")
        val testMerchantOrderId = "TEST_MERCHANT_ORDER_ID"

        mockClient.addResponse(
            method = HttpMethod.Get,
            url = transactionsSvcClient.baseUrl +
                "/merchants/${testPublicKey.value}/rewards_for_confirmed_checkout/$testMerchantOrderId",
            responseBody = responseJson,
        )

        runBlocking {
            val response = transactionsSvcClient.fetchRewardsForConfirmedCheckout(
                orderId = testMerchantOrderId, publicKey = testPublicKey
            )
            when (response) {
                is NetworkResponse.Success -> {
                    assertThat(response.body.earnedRewards).isEqualTo(2424)
                    assertThat(response.body.userDonationAmountFromReward).isEqualTo(24)
                }

                is NetworkResponse.Failure -> fail(
                    "Valid request should succeed and return rewards data"
                )
            }
        }
    }
}
