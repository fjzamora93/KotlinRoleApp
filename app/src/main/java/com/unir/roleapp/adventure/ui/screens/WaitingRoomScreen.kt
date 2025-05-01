package com.unir.roleapp.adventure.ui.screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.unir.roleapp.adventure.domain.model.Character

@Composable
fun WaitingRoomScreen(
    adventureId: String,
    characters: List<Character>,
    loading: Boolean = false,
    error: String? = null,
    onContinue: () -> Unit
) {
    // ➡️ Debug to verify that the ID is arriving in the UI layer
    LaunchedEffect(adventureId) {
        Log.d("WaitingRoomScreen", "Received adventureId = $adventureId")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1️⃣ Title + raw ID
        Text(
            text = "Sala de espera",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = adventureId,
            style = MaterialTheme.typography.displayMedium
        )

        Spacer(Modifier.height(24.dp))

        // 2️⃣ Placeholder row: show up to 4 characters or “?”
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(4) { index ->
                val content = characters.getOrNull(index)?.name ?: "?"
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(2.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = content,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }

        Spacer(Modifier.weight(1f))

        // 3️⃣ Continue button, enabled when ≥2 players have joined
        Button(
            onClick = onContinue,
            enabled = characters.size >= 2,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continuar")
        }
    }
}
