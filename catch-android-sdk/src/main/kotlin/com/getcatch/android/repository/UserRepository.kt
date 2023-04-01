package com.getcatch.android.repository

import com.getcatch.android.models.PublicUserData
import kotlinx.coroutines.flow.StateFlow

internal interface UserRepository {
    val activeUser: StateFlow<PublicUserData?>
    val didFetchUserData: StateFlow<Boolean>
    var deviceToken: String?

    suspend fun loadUserData(merchantId: String)
    fun fallbackToNewUser()
}
