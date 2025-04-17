package com.unir.roleapp.character.ui.screens.spells.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.roleapp.character.data.model.local.Item
import com.roleapp.character.data.model.local.Spell
import com.roleapp.character.ui.viewmodels.CharacterViewModel
import com.roleapp.core.ui.components.common.DefaultColumn
import com.roleapp.core.ui.components.common.DefaultRow
import com.roleapp.core.ui.theme.CustomType
import com.unir.roleapp.R
import com.unir.roleapp.character.data.model.local.ItemCategory
import com.unir.roleapp.character.data.model.local.StatName


@Composable
fun SpellSummaryComponent(
    spell: Spell,
    characterViewModel: CharacterViewModel = hiltViewModel(),
    onClick: () -> Unit,
) {
    val currentCharacter by characterViewModel.selectedCharacter.collectAsState()

    currentCharacter?.let {
        DefaultRow{
            // Columan principal del objeto
            // Insertar una imagen del objeto (item.category) con un WHEN ... simplemente apunta a R.drawable, yo decido lo que hacer con cada uno
            Box(
                modifier = Modifier
                    .size(60.dp)  // Ajusta el tamaño de la imagen según lo necesites
                    .clip(RoundedCornerShape(8.dp))  // Bordes redondeados
                    .background(Color.LightGray)  // Fondo del recuadro
                    .padding(1.dp)  // Espacio entre la imagen y el borde del recuadro
            ) {
                // Carga la imagen según la categoría del item
                when (spell.level) {
                    1 -> Image(
                        painter = painterResource(id = R.drawable.baseline_filter_1_24),
                        contentDescription = "Category 1",
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> Image(
                        painter = painterResource(id = R.drawable.baseline_filter_2_24),
                        contentDescription = "Category 2",
                        modifier = Modifier.fillMaxSize()
                    )
                    3 -> Image(
                        painter = painterResource(id = R.drawable.baseline_filter_3_24),
                        contentDescription = "Category 2",
                        modifier = Modifier.fillMaxSize()
                    )

                    4 -> Image(
                        painter = painterResource(id = R.drawable.baseline_filter_4_24),
                        contentDescription = "Category 2",
                        modifier = Modifier.fillMaxSize()
                    )

                    5 -> Image(
                        painter = painterResource(id = R.drawable.baseline_filter_5_24),
                        contentDescription = "Category 2",
                        modifier = Modifier.fillMaxSize()
                    )

                    6 -> Image(
                        painter = painterResource(id = R.drawable.baseline_filter_6_24),
                        contentDescription = "Category 2",
                        modifier = Modifier.fillMaxSize()
                    )
                    // Agrega más categorías según sea necesario
                    else -> Image(
                        painter = painterResource(id = R.drawable.baseline_filter_6_24),
                        contentDescription = "Default Image",
                        modifier = Modifier.fillMaxSize()
                    )



                }

            }

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(3f),
            ) {
                // Nombre y gold value alineados con espacio entre ellos
                DefaultRow(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = spell.name,
                        style = CustomType.titleMedium
                    )

                    Text(
                        text = " ${spell.cost} Ap" + "🌟",
                        style = CustomType.bodySmall
                    )
                }

                Text(
                    text = "${ spell.diceAmount }d${ spell.dice }. ${spell.description}",
                )

            }

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(0.5f),
            ){
                IconButton(
                    onClick = { onClick() }
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Más opciones",
                    )
                }
            }


        }
    }
}

