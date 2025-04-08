package com.unir.roleapp.character.ui.screens.items.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.core.ui.theme.MedievalColours


@Composable
fun CurrentGoldComponent(
    characterViewModel: CharacterViewModel = hiltViewModel(),
    modifier: Modifier = Modifier.fillMaxWidth()
){
    val selectedCharacter by characterViewModel.selectedCharacter.collectAsState()
    var goldText by remember { mutableStateOf(selectedCharacter?.gold?.toString() ?: "0") }
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
        modifier = modifier
    ){
        Icon(imageVector = Icons.Default.MonetizationOn, contentDescription = "", tint = MedievalColours.Gold, modifier = Modifier.size(40.dp))

        TextField(
            value = goldText,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    goldText = newValue
                    val currentCharacter = characterViewModel.selectedCharacter.value
                    currentCharacter!!.gold = newValue.toIntOrNull() ?: 0
                    characterViewModel.saveCharacter(currentCharacter)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            label = { Text("") }
        )

    }



}
