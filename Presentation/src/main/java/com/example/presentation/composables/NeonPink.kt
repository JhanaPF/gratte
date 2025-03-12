package com.example.presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.theme.Black
import com.example.presentation.theme.NeonPink
import com.example.presentation.theme.retro

@Composable
fun RetroNeonButton(
    modifier: Modifier = Modifier,
    text: String,
    size: NeonButtonSize = NeonButtonSize.Medium,
    borderColor: Color = NeonPink,
    fontFamily: FontFamily = retro,
    onClick: () -> Unit,
) {
    Button(
        onClick = { onClick() },
        shape = RectangleShape,
        border = BorderStroke(2.dp, borderColor), // The neon border
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = modifier
            .height(size.height)
            .width(size.width),
    ) {
        Text(
            text = text,
            color = Black,
            fontSize = size.fontSize.sp,
            fontFamily = fontFamily,
            modifier = Modifier.padding(8.dp),
        )
    }
}

enum class NeonButtonSize(
    internal val height: Dp,
    internal val width: Dp,
    internal val fontSize: Int,
) {
    Large(
        height = 90.dp,
        width = 400.dp,
        fontSize = 24,
    ),
    Medium(
        height = 60.dp,
        width = 300.dp,
        fontSize = 20,
    ),
    Small(
        height = 48.dp,
        width = 200.dp,
        fontSize = 14,
    ),
}

@Preview
@Composable
private fun RetroNeonButtonLargePreview() {
    RetroNeonButton(
        text = "Send It",
        size = NeonButtonSize.Large,
        onClick = {},
    )
}

@Preview
@Composable
private fun RetroNeonButtonMediumPreview() {
    RetroNeonButton(
        text = "Send It",
        size = NeonButtonSize.Medium,
        onClick = {},
    )
}

@Preview
@Composable
private fun RetroNeonButtonSmallPreview() {
    RetroNeonButton(
        text = "Send It",
        size = NeonButtonSize.Small,
        onClick = {},
    )
}
