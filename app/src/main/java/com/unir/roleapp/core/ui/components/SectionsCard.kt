package com.unir.roleapp.core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.unir.roleapp.R

@Composable
fun SectionCard(
    title: String,
    description: String,
    painter: Painter,
    onClick: () -> Unit // Parámetro para manejar el click
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .clickable(onClick = onClick)
            .background(color = colorResource(id = R.color.navy))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(4.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    if (painter != null) {
                        Image(
                            painter = painter,
                            contentDescription = "Card Image",
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
