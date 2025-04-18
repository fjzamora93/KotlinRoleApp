package com.roleapp.character.ui.screens.common

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.ui.viewmodels.ItemViewModel
import com.unir.roleapp.R
import com.unir.roleapp.character.data.model.local.StatName
@Composable
fun InlineStat(
    skillName: StatName,
    localValue: Int = 10,
    label: String,
    modifier: Modifier = Modifier.padding(bottom = 8.dp),
    itemViewModel: ItemViewModel = hiltViewModel()
) {
    val modifyingStats = itemViewModel.modifyingStats.collectAsState()
    val modifiedValue = modifyingStats.value.find { it.type == skillName }?.modifyingValue ?: 0
    val displayValue = localValue + modifiedValue

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
    label: String,
    value: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Text(
                text = value.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
    Spacer(modifier = Modifier
        .height(16.dp)
        .width(10.dp))

}
