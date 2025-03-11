package com.example.pixeliseit.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pixeliseit.presentation.theme.retro

@Composable
fun ScoreRow(
    modifier: Modifier = Modifier,
    color: Color,
    rank: Int,
    score: Int,
    name: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            color = color,
            fontFamily = retro,
            text = rank.toString(),
            maxLines = 1,
        )
        Text(
            color = color,
            fontFamily = retro,
            text = score.toString(),
            maxLines = 1,
        )
        Text(
            color = color,
            fontFamily = retro,
            text = name,
            maxLines = 1,
        )
    }
}

@Preview
@Composable
fun ScoreRowPreview() {
    ScoreRow(
        rank = 1,
        score = 1000,
        name = "John Doe",
        color = Color.Red
    )
}