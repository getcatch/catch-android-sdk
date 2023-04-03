package com.getcatch.android.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.getcatch.android.models.Item
import com.getcatch.android.models.PublicUserData
import com.getcatch.android.repository.RewardsRepository
import com.getcatch.android.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class EarnRedeemViewModel(
    private val userRepo: UserRepository,
    private val rewardsRepo: RewardsRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<EarnRedeemUiState>(EarnRedeemUiState.Loading)
    val uiState: StateFlow<EarnRedeemUiState> = _uiState.asStateFlow()

    private var price: Int? = null
    private var items: List<Item>? = null
    private var userCohorts: List<String>? = null

    fun init(price: Int, items: List<Item>?, userCohorts: List<String>?) {
        this.price = price
        this.items = items
        this.userCohorts = userCohorts

        viewModelScope.launch {
            userRepo.activeUser.collect { user ->
                if (user != null) {
                    calculateReward(user)
                }
            }
        }
    }

    private suspend fun calculateReward(user: PublicUserData) {
        price?.let {
            _uiState.value = EarnRedeemUiState.Loading
            val calculatedReward = rewardsRepo.fetchCalculatedEarnedReward(
                user = user,
                price = it,
                items = items,
                userCohorts = userCohorts,
            )
            _uiState.value = EarnRedeemUiState.Success(calculatedReward)
        }
    }
}
