package com.neotica.jetrewards

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.neotica.jetrewards.ui.theme.JetRewardsTheme

@Composable
fun JetRewardApp(
    modifier: Modifier = Modifier,
) {

}

@Preview(showBackground = true)
@Composable
fun JetHeroesAppPreview() {
    JetRewardsTheme {
        JetRewardApp()
    }
}
