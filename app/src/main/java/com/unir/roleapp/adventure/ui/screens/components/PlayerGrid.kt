package com.unir.roleapp.adventure.ui.screens.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.roleapp.core.di.LocalNavigationViewModel
import com.roleapp.core.navigation.NavigationViewModel
import com.roleapp.core.navigation.ScreensRoutes
import com.unir.roleapp.adventure.domain.model.AdventureCharacter

@SuppressLint("DiscouragedApi")
@Composable
fun PlayerGrid(
    players: List<AdventureCharacter>,
    navigationViewModel: NavigationViewModel = LocalNavigationViewModel.current
) {
    val totalSlots = 4
    val columns = 4
    val rows = totalSlots / columns

    val context = LocalContext.current
    val packageName = context.packageName

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
                        contentAlignment = Alignment.Center,
                    ) {
                        if (index < players.size) {

                            val resName = players[index].imgUrl

                            val resId = remember(resName) {
                                context.resources.getIdentifier(
                                    resName,
                                    "drawable",
                                    packageName
                                )
                            }

                            if (resId != 0) {
                                Image(
                                    painter = painterResource(id = resId),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(
                                            2.dp,
                                            MaterialTheme.colorScheme.onSurface,
                                            RoundedCornerShape(16.dp)
                                        )
                                        .clickable {
                                            navigationViewModel.navigate(ScreensRoutes.CharacterDetailScreen.createRoute(players[index].id))
                                        },
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Text(
                                    text = players[index].name.take(2).uppercase(),
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.White,
                                    modifier = Modifier.clickable {
                                        navigationViewModel.navigate(ScreensRoutes.CharacterDetailScreen.createRoute(players[index].id))
                                    }
                                )
                            }

                        } else {
                            Text(
                                text = "?",
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
