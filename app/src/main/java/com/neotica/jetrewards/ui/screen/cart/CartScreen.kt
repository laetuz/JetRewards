package com.neotica.jetrewards.ui.screen.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.neotica.jetrewards.R
import com.neotica.jetrewards.ui.common.UiState
import com.neotica.jetrewards.ui.components.CartItem
import com.neotica.jetrewards.ui.components.OrderButton
import com.neotica.jetrewards.ui.theme.JetRewardsTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = getViewModel()
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        when (it) {
            is UiState.Loading -> {
                viewModel.getAddedOrderRewards()
            }

            is UiState.Success -> {
                CartContent(
                    state = it.data,
                    onProductCountChanged = { rewardId, count ->
                        viewModel.updateOrderReward(rewardId, count)
                    }
                )
            }

            is UiState.Error -> {
                it.errorMessage
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                TopBarCart {}
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Text(
                    text = stringResource(id = R.string.menu_cart),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ){
                    items(state.orderReward, key = {it.reward.id}) {
                        CartItem(
                            rewardId = it.reward.id,
                            image = it.reward.image,
                            title = it.reward.title,
                            totalPoint = it.reward.requiredPoint,
                            count = it.count,
                            onProductCountChanged = onProductCountChanged
                        )
                        Divider()
                    }
                }
                OrderButton(
                    text = stringResource(R.string.total_order, state.totalRequiredPoint),
                    enabled = state.orderReward.isNotEmpty(),
                    onClick = {},
                    modifier = Modifier.padding(16.dp)
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarCart(onMenuClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = stringResource(id = R.string.app_name)) }
    )
}

@Preview(showBackground = true)
@Composable
fun CartPreview() {
    JetRewardsTheme {
        CartScreen()
    }
}