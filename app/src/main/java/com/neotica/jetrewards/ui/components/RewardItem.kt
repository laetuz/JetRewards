package com.neotica.jetrewards.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.neotica.jetrewards.R
import com.neotica.jetrewards.ui.theme.JetRewardsTheme

@Composable
fun RewardItem(
    image: Int,
    title: String,
    requiredPoint: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(170.dp)
                .clip(shape = MaterialTheme.shapes.medium)
        )
        Text(
            text = title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )
        Text(
            text = stringResource(R.string.required_point, requiredPoint),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RewardItemPreview() {
    JetRewardsTheme {
        RewardItem(R.drawable.reward_4, "Jaket Hoodie Dicoding", 4000)
    }
}