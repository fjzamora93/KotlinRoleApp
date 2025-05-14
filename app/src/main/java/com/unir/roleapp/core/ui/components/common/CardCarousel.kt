package com.unir.roleapp.core.ui.components.common

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.*
import com.unir.roleapp.core.ui.components.sectioncard.SectionCard
import com.unir.roleapp.core.ui.components.sectioncard.SectionCardItem

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CardCarousel(
        cardItems: List<SectionCardItem>,
        pagerState: PagerState,
        navController: NavHostController,
        isAdventure: Boolean = false) {

    HorizontalPager(
        count = cardItems.size,
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 50.dp),
        contentPadding = PaddingValues(horizontal = 60.dp)
    ) { page ->
        val targetScale = if (page == pagerState.currentPage) 1.05f else 0.8f
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsHoveredAsState()
        val isHover by interactionSource.collectIsPressedAsState()
        val pressScale by animateFloatAsState(targetValue = if (isPressed || isHover) 0.95f else 1.05f)
        val finalScale = targetScale * pressScale

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    scaleX = finalScale
                    scaleY = finalScale
                }
        ) {
            SectionCard(
                cardItems[page].title,
                cardItems[page].description,
                cardItems[page].imageResName,
                onClick = {
                    navController.navigate(cardItems[page].route)
                },
                isAdventure,
                interactionSource = interactionSource
            )
        }
    }
}