package com.neotica.jetrewards

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import androidx.navigation.navArgument
import com.neotica.jetrewards.ui.navigation.NavigationItem
import com.neotica.jetrewards.ui.navigation.Screen
import com.neotica.jetrewards.ui.screen.cart.CartScreen
import com.neotica.jetrewards.ui.screen.detail.DetailScreen
import com.neotica.jetrewards.ui.screen.home.HomeScreen
import com.neotica.jetrewards.ui.screen.profile.ProfileScreen
import com.neotica.jetrewards.ui.theme.JetRewardsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetRewardApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val graph = navController.createGraph(startDestination = Screen.Home.route) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToDetail = {
                    navController.navigate(Screen.DetailReward.createRoute(it))
                }
            )
        }
        composable(Screen.Cart.route) {
            val context = LocalContext.current
            CartScreen(
                orderButtonClicked = { shareOrder(context, it) }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(
            route = Screen.DetailReward.route,
            arguments = listOf(navArgument("rewardId") { type = NavType.LongType })
        ) {
            val id = it.arguments?.getLong("rewardId") ?: -1L
            DetailScreen(
                rewardId = id,
                navigateBack = { navController.navigateUp() },
                navigateToCart = {
                    navController.popBackStack()
                    navController.navigate(Screen.Cart.route){
                        popUpTo(navController.graph.findStartDestination().id){saveState = true}
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
    Scaffold(
        bottomBar = { if (currentRoute!=Screen.DetailReward.route) {
            BottomBar(navController, modifier)
        } },
        modifier = modifier
    ) {
        NavHost(
            navController = navController,
            graph = graph,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(modifier = modifier) {
        val navigation = listOf(
            NavigationItem(
                title = stringResource(id = R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home,
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_home),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart,
            ),
            NavigationItem(
                title = stringResource(id = R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            )
        )
        navigation.map {
            NavigationBarItem(
                selected = currentRoute == it.screen.route,
                onClick = {
                    navController.navigate(it.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = { Icon(imageVector = it.icon, contentDescription = it.title) },
                label = { Text(text = it.title) }
            )
        }
    }
}

private fun shareOrder(context: Context, summary: String) {
    val intent  = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.dicoding_reward))
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.dicoding_reward)
        )
    )
}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    JetRewardsTheme {
        JetRewardApp()
    }
}