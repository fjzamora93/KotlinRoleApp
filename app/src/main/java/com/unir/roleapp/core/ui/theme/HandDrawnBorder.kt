package com.unir.roleapp.core.ui.theme

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.unir.roleapp.core.ui.theme.CustomColors
import kotlin.random.Random

fun Modifier.handDrawnBorder(
    color: Color,
    strokeWidth: Dp = 2.dp,
    steps: Int = 20,
    jitter: Float = 8f
): Modifier = this.then(
    Modifier.drawBehind {
        val strokePx = strokeWidth.toPx()

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
            jitterLine(topLeft, topRight),
            jitterLine(topRight, bottomRight),
            jitterLine(bottomRight, bottomLeft),
            jitterLine(bottomLeft, topLeft)
        )

        paths.forEach { path ->
            drawPath(
                path = path,
                color = color,
                style = Stroke(width = strokePx)
            )
        }
    }
)



fun Modifier.doubleHandDrawnBorder(
    color: Color = Color.DarkGray,
    strokeWidth: Dp = 2.dp,
    steps: Int = 40,
    jitter: Float = 8f,
    offsetJitter: Float = 4f // Desfase extra entre los dos trazos
): Modifier = this.then(
    Modifier.drawBehind {
        val strokePx = strokeWidth.toPx()

        fun jitterLine(from: Offset, to: Offset, extraJitter: Float = 0f): Path {
            val path = Path()
            val dx = (to.x - from.x) / steps
            val dy = (to.y - from.y) / steps

            path.moveTo(from.x, from.y)
            for (i in 1..steps) {
                val x = from.x + dx * i +
                        Random.nextFloat() * (jitter + extraJitter) - (jitter + extraJitter) / 2
                val y = from.y + dy * i +
                        Random.nextFloat() * (jitter + extraJitter) - (jitter + extraJitter) / 2
                path.lineTo(x, y)
            }

            return path
        }

        val topLeft = Offset(0f, 0f)
        val topRight = Offset(size.width, 0f)
        val bottomRight = Offset(size.width, size.height)
        val bottomLeft = Offset(0f, size.height)

        val borderPairs = listOf(
            topLeft to topRight,
            topRight to bottomRight,
            bottomRight to bottomLeft,
            bottomLeft to topLeft
        )

        // Dibuja dos trazos por cada lado
        repeat(2) { i ->
            val extraJitter = if (i == 0) 0f else offsetJitter
            borderPairs.forEach { (from, to) ->
                val path = jitterLine(from, to, extraJitter)
                drawPath(
                    path = path,
                    color = color,
                    style = Stroke(width = strokePx)
                )
            }
        }
    }
)

fun Modifier.wavyBorder(
    color: Color = Color.DarkGray,
    strokeWidth: Dp = 2.dp,
    waveAmplitude: Float = 6f,
    waveFrequency: Int = 6
): Modifier = this.then(
    Modifier.drawBehind {
        val strokePx = strokeWidth.toPx()

        fun waveLine(from: Offset, to: Offset): Path {
            val path = Path()
            val length = (to - from).getDistance()
            val direction = (to - from) / length
            val normal = Offset(-direction.y, direction.x)

            val segmentLength = length / waveFrequency
            path.moveTo(from.x, from.y)

            for (i in 1..waveFrequency) {
                val t = i / waveFrequency.toFloat()
                val mid = from + (to - from) * t
                val controlOffset = normal * waveAmplitude * if (i % 2 == 0) -1f else 1f

                val prev = from + (to - from) * ((i - 1) / waveFrequency.toFloat())
                val controlPoint = (prev + mid) / 2F + controlOffset

                path.quadraticBezierTo(
                    controlPoint.x,
                    controlPoint.y,
                    mid.x,
                    mid.y
                )
            }

            return path
        }

        val topLeft = Offset(0f, 0f)
        val topRight = Offset(size.width, 0f)
        val bottomRight = Offset(size.width, size.height)
        val bottomLeft = Offset(0f, size.height)

        val borderPairs = listOf(
            topLeft to topRight,
            topRight to bottomRight,
            bottomRight to bottomLeft,
            bottomLeft to topLeft
        )

        borderPairs.forEach { (from, to) ->
            val path = waveLine(from, to)
            drawPath(
                path = path,
                color = color,
                style = Stroke(width = strokePx)
            )
        }
    }
)

