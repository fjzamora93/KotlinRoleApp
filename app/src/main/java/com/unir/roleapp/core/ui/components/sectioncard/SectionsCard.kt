package com.unir.roleapp.core.ui.components.sectioncard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.unir.roleapp.R

@Composable
fun SectionCard(
    title: String,
    description: String,
    imageResName: String,
    onClick: () -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val context = LocalContext.current
    val imageResId = remember(imageResName) {
        context.resources.getIdentifier(
            imageResName,
            "drawable",
            context.packageName
        )
    }
    val painter = if (imageResId != 0) painterResource(id = imageResId) else null

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(),
                onClick = onClick
            ),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.dark_navy))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, start = 8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = colorResource(id = R.color.white)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(4.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        painter?.let {
                            Image(
                                painter = it,
                                contentDescription = "Card Image",
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                            .background(colorResource(id = R.color.light_yellow)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = description,
                            modifier = Modifier
                                .height(240.dp)
                                .width(200.dp)
                                .wrapContentHeight(),
                            style = MaterialTheme.typography.bodyMedium,

                        )
                    }
                }
            }
        }
    }
}
