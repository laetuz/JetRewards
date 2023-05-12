package com.neotica.jetrewards.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neotica.jetrewards.data.RewardRepository
import com.neotica.jetrewards.model.OrderReward
import com.neotica.jetrewards.model.Reward
import com.neotica.jetrewards.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailRewardViewModel(
    private val repository: RewardRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderReward>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderReward>>
        get() = _uiState

    fun getRewardById(rewardId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderRewardById(rewardId))
        }
    }

    fun addToCart(reward: Reward, count: Int) {
        viewModelScope.launch {
            repository.updateOrderReward(reward.id, count)
        }
    }
}