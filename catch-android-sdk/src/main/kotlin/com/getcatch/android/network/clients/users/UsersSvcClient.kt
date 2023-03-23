package com.getcatch.android.network.clients.users

internal interface UsersSvcClient {
    suspend fun fetchUserData(deviceToken: String, merchantId: String)
}
