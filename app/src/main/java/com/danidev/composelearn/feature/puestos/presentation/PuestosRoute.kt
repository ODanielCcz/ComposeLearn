package com.danidev.composelearn.feature.puestos.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun PuestosRoute(
    viewModel: PuestosViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    PuestosScreen(
        uiState = uiState,
        onSearchChange = viewModel::onSearchChange,
        onAddPuesto = viewModel::addPuesto,
        onEditPuesto = { puesto ->
            viewModel.updatePuesto(
                id = puesto.id,
                nombre = puesto.nombre,
                descripcion = puesto.descripcion
            )
        },
        onDeletePuesto = { puesto ->
            viewModel.deletePuesto(puesto.id)
        },
        isPuestoValid = viewModel::isPuestoValid
    )
}