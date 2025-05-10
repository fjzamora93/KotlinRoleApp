    package com.unir.roleapp.adventure.ui.screens

    import android.util.Log
    import androidx.compose.foundation.Image
    import androidx.compose.foundation.border
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.layout.ContentScale
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.unit.dp
    import androidx.hilt.navigation.compose.hiltViewModel
    import androidx.navigation.NavHostController
    import com.roleapp.core.di.LocalNavigationViewModel
    import com.roleapp.core.navigation.NavigationViewModel
    import com.roleapp.core.navigation.ScreensRoutes
    import com.roleapp.core.ui.layout.MainLayout

    import com.unir.roleapp.R
    import com.unir.roleapp.adventure.ui.viewmodels.CreateAdventureViewModel

    @Composable
    fun AdventureMainScreen(
        navController: NavigationViewModel = LocalNavigationViewModel.current,
        viewModel: CreateAdventureViewModel = hiltViewModel()
    ) {
        // 1) Recoge el estado del ViewModel
        val title by viewModel.title.collectAsState()
        val description by viewModel.description.collectAsState()

        MainLayout {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(32.dp))

                Image(
                    painter = painterResource(id = R.drawable.fantasy_landscape),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(2.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.height(24.dp))

                Text("Nueva aventura", style = MaterialTheme.typography.titleLarge)

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = viewModel::onTitleChange,
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = viewModel::onDescriptionChange,
                    label = { Text("Descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                )

                Spacer(Modifier.height(24.dp))

                // 2) Botón dentro del mismo Column, centrado
                Button(
                    onClick = {
                        viewModel.createAdventure { adv ->
                            Log.d("AdventureMain", "ID generado: ${adv.id}")
                            navController.navigate(
                                ScreensRoutes.WaitingRoomScreen.createRoute(adv.id)
                            )
                        }


                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text("Crear aventura")
                }
            }
        }
    }
