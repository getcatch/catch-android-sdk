package com.getcatch.android.repository

import com.getcatch.android.cache.CacheManager
import com.getcatch.android.models.PublicUserData
import com.getcatch.android.network.NetworkResponse
import com.getcatch.android.network.clients.transactions.TransactionsSvcClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class UserRepositoryImpl(
    private val transactionsSvcClient: TransactionsSvcClient,
    private val cache: CacheManager,
) : UserRepository {
    private val _activeUser = MutableStateFlow<PublicUserData?>(null)
    override val activeUser: StateFlow<PublicUserData?> = _activeUser.asStateFlow()

    private val _didFetchUserData = MutableStateFlow(false)
    override val didFetchUserData: StateFlow<Boolean> = _didFetchUserData.asStateFlow()

    override var deviceToken: String?
        get() = cache.deviceToken
        set(value) { cache.deviceToken = value }

    override suspend fun loadUserData(merchantId: String) {
        val token = deviceToken
        if (token != null) {
            val response =
                transactionsSvcClient.fetchUserData(deviceToken = token, merchantId = merchantId)
            when (response) {
                is NetworkResponse.Success -> _activeUser.value = response.body
                is NetworkResponse.Failure -> _activeUser.value = PublicUserData.noData
            }
        } else {
            _activeUser.value = PublicUserData.noData
        }

        _didFetchUserData.value = true
    }

    override fun fallbackToNewUser() {
        _activeUser.value = PublicUserData.noData
        _didFetchUserData.value = true
    }
}
