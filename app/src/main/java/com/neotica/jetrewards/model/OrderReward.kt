package com.neotica.jetrewards.model

import com.neotica.jetrewards.model.Reward

data class OrderReward(
    val reward: Reward,
    val count: Int
)