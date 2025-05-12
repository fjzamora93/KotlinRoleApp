package com.unir.roleapp.adventure.ui.screens.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.unir.roleapp.adventure.domain.model.Character

@Composable
fun PlayerGrid(players: List<Character>, onAddPlayer: () -> Unit) {
    val totalSlots = 4
    val columns = 2
    val rows = totalSlots / columns

    Column {
        for (row in 0 until rows) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                for (col in 0 until columns) {
                    val index = row * columns + col
                    Box(
                        modifier = Modifier
                            .weight(1.5f)
                            .aspectRatio(1.5f)
                            .border(2.dp, Color.White)
                            .clickable {
                                if (index >= players.size) {
                                    onAddPlayer()
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        if (index < players.size) {
                            Text(players[index].name, style = MaterialTheme.typography.bodyLarge)
                        } else {
                            Text("+", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}