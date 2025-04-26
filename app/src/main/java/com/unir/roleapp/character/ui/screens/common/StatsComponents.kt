package com.roleapp.character.ui.screens.common
import androidx.constraintlayout.compose.ConstraintLayout

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.ui.screens.common.dialogues.CharacterDialog
import com.roleapp.character.ui.viewmodels.ItemViewModel
import com.unir.roleapp.R
import com.unir.roleapp.character.data.model.local.StatName
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlin.io.path.moveTo
import androidx.compose.ui.graphics.StampedPathEffectStyle
import com.roleapp.core.ui.theme.CustomColors
import com.unir.roleapp.core.ui.theme.doubleHandDrawnBorder
import com.unir.roleapp.core.ui.theme.handDrawnBorder
import com.unir.roleapp.core.ui.theme.wavyBorder
import kotlin.random.Random

@Composable
fun InlineStat(
    skillName: StatName,
    localValue: Int = 10,
    label: String,
    modifier: Modifier = Modifier.padding(bottom = 8.dp),
    itemViewModel: ItemViewModel = hiltViewModel()
) {
    val modifyingStats = itemViewModel.modifyingStats.collectAsState()
    val modifiedValue  by remember { mutableIntStateOf( modifyingStats.value.find { it.type == skillName }?.modifyingValue ?: 0 ) }
    val displayValue by remember { mutableIntStateOf(localValue + modifiedValue) }

    val borderColor = when {
        modifiedValue > 0 -> Color(0xFF4CAF50) // Verde
        modifiedValue < 0 -> Color(0xFFF44336) // Rojo
        else -> MaterialTheme.colorScheme.outline
    }

    val arrowPainter = when {
        modifiedValue > 0 -> painterResource(id = R.drawable.baseline_keyboard_double_arrow_up_24)
        modifiedValue < 0 -> painterResource(id = R.drawable.baseline_keyboard_double_arrow_down_24)
        else -> null
    }

    val arrowColor = when {
        modifiedValue > 0 -> Color(0xFF4CAF50)
        modifiedValue < 0 -> Color(0xFFF44336)
        else -> Color.Transparent
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .border(1.dp, borderColor, RoundedCornerShape(4.dp))
                .padding(6.dp)
        ) {
            Text(
                "$displayValue",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        if (arrowPainter != null) {
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = arrowPainter,
                contentDescription = null,
                tint = arrowColor,
                modifier = Modifier.size(18.dp)
            )
        }

        Spacer(Modifier.width(8.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
fun NumberBox(
    stat: StatName,
    value: Int,
    modifier: Modifier = Modifier
) {


    ConstraintLayout(
        modifier = modifier.wrapContentSize()
    ) {
        val (cardRef, badgeRef) = createRefs()

        // 1) Tarjeta principal con trazo "hecho a mano"
        Box(
            modifier = Modifier
                .constrainAs(cardRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                // fondo liso
                .background(
                    brush = CustomColors.BlackGradient,
                    shape = RoundedCornerShape(8.dp)
                )
                .doubleHandDrawnBorder()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = StatName.getString(stat),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = Color.White
                )

                HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp))


                Box(
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                        .widthIn(min = 30.dp),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        text = value.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.Black
                    )
                }
            }
        }

        // 2) Óvalo superpuesto (badge)
        Box(
            modifier = Modifier
                .wavyBorder()
                .size(48.dp, 32.dp)
                .background(color = MaterialTheme.colorScheme.secondary,)
                .constrainAs(badgeRef) {
                    top.linkTo(cardRef.bottom, margin = (-16).dp)
                    centerHorizontallyTo(cardRef)
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = StatName.getIcon(stat)),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}
