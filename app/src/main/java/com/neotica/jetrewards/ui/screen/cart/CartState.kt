package com.neotica.jetrewards.ui.screen.cart

import com.neotica.jetrewards.model.OrderReward

data class CartState(
    val orderReward: List<OrderReward>,
    val totalRequiredPoint: Int
)