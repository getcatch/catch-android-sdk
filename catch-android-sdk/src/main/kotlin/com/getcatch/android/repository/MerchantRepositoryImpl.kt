package com.getcatch.android.repository

import android.util.Log
import com.getcatch.android.cache.CacheManager
import com.getcatch.android.clients.merchants.MerchantsSvcClient
import com.getcatch.android.domain.Merchant
import com.getcatch.android.mappers.toMerchant
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.UnknownHostException

internal class MerchantRepositoryImpl(
    val merchantsSvcClient: MerchantsSvcClient,
    val cache: CacheManager,
) : MerchantRepository {
    private val _activeMerchant = MutableStateFlow(cache.merchant)
    override val activeMerchant: StateFlow<Merchant?> = _activeMerchant.asStateFlow()

    override suspend fun loadMerchant(publicKey: String) {
        try {
            val response = merchantsSvcClient.loadPublicMerchantData(
                publicKey = publicKey
            )
            val updatedMerchant = response.toMerchant(publicKey = publicKey)
            _activeMerchant.value = updatedMerchant
            cache.merchant = updatedMerchant
        } catch (ex: UnknownHostException) {
            Log.w(
                MerchantRepositoryImpl::class.simpleName,
                "Network request failed. Check network connection and try again.",
                ex
            )
        } catch (ex: JsonConvertException) {
            Log.e(
                MerchantRepositoryImpl::class.simpleName,
                "Error deserializing loadMerchant response",
                ex
            )
        } catch (ex: NoTransformationFoundException) {
            Log.e(
                MerchantRepositoryImpl::class.simpleName,
                "Response body did not match expected format",
                ex
            )
        }
    }
}
