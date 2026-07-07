package com.danidev.composelearn.feature.home.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun HomeRoute(
    onPuestosClick: () -> Unit,
    onPersonalClick: () -> Unit
) {
    val uiState = remember { HomeUiState() }

    HomeScreen(
        uiState = uiState,
        onOptionClick = { option ->
            when (option) {
                HomeOption.HomePuestos -> onPuestosClick()
                HomeOption.HomePersonal -> onPersonalClick()
            }
        }
    )
}