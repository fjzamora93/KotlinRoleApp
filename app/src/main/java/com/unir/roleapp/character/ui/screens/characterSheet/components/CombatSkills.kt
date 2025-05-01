package com.unir.roleapp.character.ui.screens.characterSheet.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.unir.roleapp.character.ui.viewmodels.SkillViewModel
import com.unir.roleapp.core.ui.theme.CustomColors
import com.unir.roleapp.character.data.model.local.StatName
import com.unir.roleapp.core.ui.theme.wavyBorder

@Composable
fun CombatSkillSection(
    skillViewModel: SkillViewModel = hiltViewModel()
) {
    val skillList by skillViewModel.skillList.collectAsState()
    val combatSkills = skillList.filter { it.skill.tag == StatName.COMBAT }.sortedByDescending { it.value }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        combatSkills.forEach { skill ->
            val context = LocalContext.current // Obtiene el contexto de la app
            val resourceId = context.resources.getIdentifier(skill.skill.getIcon(), "drawable", context.packageName)

            Box(
                modifier = Modifier
                    .background(CustomColors.BlackGradient)
                    .wavyBorder()
                    .width(80.dp)
                    .padding(4.dp)
                    .border(2.dp, Color.Gray, RoundedCornerShape(12.dp))
                    .padding(6.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {



                    IconButton(
                        onClick = { /* Acción al hacer clic en la habilidad */ },
                        modifier = Modifier.size(45.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = resourceId),
                            contentDescription = "Combat Skill",
                            tint = Color.Gray
                        )

                    }

                    Box(
                        modifier = Modifier
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = skill.value.toString(),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}





