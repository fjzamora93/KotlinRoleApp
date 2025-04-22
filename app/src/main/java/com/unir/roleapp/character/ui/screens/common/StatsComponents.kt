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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.WorkspacePremium
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
    val borderColor = MaterialTheme.colorScheme.primary


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
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
                // trazos irregulares en drawBehind
                .drawBehind {
                    val strokeWidth = 2.dp.toPx()
                    val steps = 20
                    val jitter = 8f

                    fun jitterLine(from: Offset, to: Offset): Path {
                        val path = Path()
                        val dx = (to.x - from.x) / steps
                        val dy = (to.y - from.y) / steps

                        path.moveTo(from.x, from.y)
                        for (i in 1..steps) {
                            val x = from.x + dx * i + Random.nextFloat() * jitter - jitter / 2
                            val y = from.y + dy * i + Random.nextFloat() * jitter - jitter / 2
                            path.lineTo(x, y)
                        }

                        return path
                    }

                    val topLeft = Offset(0f, 0f)
                    val topRight = Offset(size.width, 0f)
                    val bottomRight = Offset(size.width, size.height)
                    val bottomLeft = Offset(0f, size.height)

                    val paths = listOf(
                        jitterLine(topLeft, topRight),       // superior
                        jitterLine(topRight, bottomRight),   // derecha
                        jitterLine(bottomRight, bottomLeft), // inferior
                        jitterLine(bottomLeft, topLeft)      // izquierda
                    )

                    paths.forEach { path ->
                        drawPath(
                            path = path,
                            color = borderColor,
                            style = Stroke(width = strokeWidth)
                        )
                    }
                }
                // dejamos espacio abajo para el badge
                .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 24.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = StatName.getString(stat),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Box(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape
                        )
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = value.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }

        // 2) Óvalo superpuesto (badge)
        Box(
            modifier = Modifier
                .size(48.dp, 32.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(50)
                )
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
