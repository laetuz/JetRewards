package com.neotica.jetrewards.di

import com.neotica.jetrewards.data.RewardRepository


object Injection {
    fun provideRepository(): RewardRepository {
        return RewardRepository.getInstance()
    }
}