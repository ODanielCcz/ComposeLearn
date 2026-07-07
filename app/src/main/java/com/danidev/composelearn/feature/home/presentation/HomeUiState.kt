package com.danidev.composelearn.feature.home.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Groups
import androidx.compose.ui.graphics.vector.ImageVector

data class HomeUiState(
    val title: String = "Inicio",
    val options: List<HomeOption> = listOf(
        HomeOption.HomePuestos,
        HomeOption.HomePersonal
    )
)

sealed class HomeOption(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector
) {
    data object HomePuestos : HomeOption(
        "puestos",
        "Puestos",
        description = "Administra puestos de trabajo",
        icon = Icons.Filled.Badge
    )
    data object HomePersonal : HomeOption(
        "personal",
        "Clientes",
        description = "Administra clientes y sus puestos asignados",
        icon = Icons.Filled.Groups
    )
}