package com.ui.components


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ui.theme.CustomType

@Composable
fun TitleLarge(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = CustomType.titleLarge,
        modifier = modifier
    )
}

@Composable
fun TitleMedium(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = CustomType.titleMedium,
        modifier = modifier
    )
}

@Composable
fun TitleSmall(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = CustomType.titleSmall,
        modifier = modifier
    )
}

@Composable
fun TextBodyLarge(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = CustomType.bodyLarge,
        modifier = modifier
    )
}

@Composable
fun TextBodyMedium(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = CustomType.bodyMedium,
        modifier = modifier
    )
}

@Composable
fun TextBodySmall(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = CustomType.bodySmall,
        modifier = modifier
    )
}

@Composable
fun TextLabelMedium(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = CustomType.labelMedium,
        modifier = modifier
    )
}