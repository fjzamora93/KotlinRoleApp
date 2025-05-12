package com.unir.roleapp.adventure.ui.screens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.roleapp.character.data.model.local.CharacterEntity

@Composable
fun PlayerGrid(players: List<CharacterEntity>) {
    val totalSlots = 4
    val columns = 4
    val rows = totalSlots / columns  // = 1

    Column {
        repeat(rows) { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                repeat(columns) { col ->
                    val index = row * columns + col
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .border(2.dp, Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        if (index < players.size) {
                            Text(
                                players[index].name,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.White
                            )
                        } else {
                            Text(
                                "?",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}