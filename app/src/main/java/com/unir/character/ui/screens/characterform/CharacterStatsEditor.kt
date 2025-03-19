package com.unir.character.ui.screens.characterform

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationResult
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.DecayAnimationSpec
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Divider
import androidx.compose.material.ProvideTextStyle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chargemap.compose.numberpicker.NumberPicker
import com.unir.character.data.model.local.CharacterEntity
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt



@Composable
fun StatSectionForm(
    character: CharacterEntity,
    onValueChange: (CharacterEntity) -> Unit
) {
    var characterToUpdate by remember(character) { mutableStateOf(character.copy()) }
    Column(modifier = Modifier.fillMaxWidth()){


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MyNumberPicker(
                label = "Carisma",
                value = character.charisma,
                onValueChange = { newValue ->
                    onValueChange(character.copy(charisma = newValue))
                }
            )

            MyNumberPicker(
                label = "Inteligencia",
                value = character.intelligence,
                onValueChange = { newValue ->
                    onValueChange(character.copy(intelligence = newValue))
                }
            )

            MyNumberPicker(
                label = "Sabiduría",
                value = character.wisdom,
                onValueChange = { newValue ->
                    onValueChange(character.copy(wisdom = newValue))
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            MyNumberPicker(
                label = "Fuerza",
                value = character.strength,
                onValueChange = { newValue ->
                    onValueChange(character.copy(strength = newValue))
                }
            )

            MyNumberPicker(
                label = "Destreza",
                value = character.dexterity,
                onValueChange = { newValue ->
                    onValueChange(character.copy(dexterity = newValue))
                }
            )

            MyNumberPicker(
                label = "Constitución",
                value = character.constitution,
                onValueChange = { newValue ->
                    onValueChange(character.copy(constitution = newValue))
                }
            )
        }
    }
}



@Composable
fun MyNumberPicker(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier:Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)

        NumberPicker(
            value = value,
            range = 1..20, // Rango de 1 a 20
            onValueChange = onValueChange
        )

        Spacer(modifier = Modifier.width(16.dp))
    }
}


