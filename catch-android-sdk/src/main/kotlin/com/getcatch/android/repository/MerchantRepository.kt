package com.getcatch.android.repository

import android.util.Log
import com.getcatch.android.clients.merchant.MerchantSvcClientImpl
import com.getcatch.android.domain.Merchant
import com.getcatch.android.mappers.toMerchant
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.serialization.JsonConvertException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.UnknownHostException

internal object MerchantRepository {
    private val _activeMerchant = MutableStateFlow<Merchant?>(null)
    val activeMerchant: StateFlow<Merchant?> = _activeMerchant.asStateFlow()

    internal suspend fun loadMerchant(publicKey: String) {
        try {
            val response = MerchantSvcClientImpl.loadPublicMerchantData(
                publicKey = publicKey
            )
            _activeMerchant.value = response.toMerchant(publicKey = publicKey)
        } catch (ex: UnknownHostException) {
            Log.w(
                MerchantRepository::class.simpleName,
                "Network request failed. Check network connection and try again.",
                ex
            )
        } catch (ex: JsonConvertException) {
            Log.e(
                MerchantRepository::class.simpleName,
                "Error deserializing loadMerchant response",
                ex
            )
        } catch (ex: NoTransformationFoundException) {
            Log.e(
                MerchantRepository::class.simpleName,
                "Response body did not match expected format",
                ex
            )
        }
    }
}
