package com.neotica.jetrewards.di

import com.neotica.jetrewards.data.RewardRepository
import com.neotica.jetrewards.ui.screen.detail.DetailRewardViewModel
import com.neotica.jetrewards.ui.screen.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModel = module {
    viewModel { DetailRewardViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    single { RewardRepository() }
}