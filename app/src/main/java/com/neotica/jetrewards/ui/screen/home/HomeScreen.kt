package com.neotica.jetrewards.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.neotica.jetrewards.model.OrderReward
import com.neotica.jetrewards.ui.common.UiState
import com.neotica.jetrewards.ui.components.RewardItem
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = getViewModel(),
    navigateToDetail: (Long) -> Unit
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {viewModel.getAllRewards()}
            is UiState.Success -> {
                HomeContent(orderReward = it.data, modifier = modifier, navigateToDetail = navigateToDetail)
            }
            is UiState.Error -> {it.errorMessage}
        }
    }
}

@Composable
fun HomeContent(
    orderReward: List<OrderReward>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    val lazyGridState = rememberLazyGridState()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        state = lazyGridState,
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(orderReward) {
            RewardItem(
                image = it.reward.image,
                title = it.reward.title,
                requiredPoint = it.reward.requiredPoint,
                modifier = Modifier.clickable{navigateToDetail(it.reward.id)}
            )
        }
    }
}