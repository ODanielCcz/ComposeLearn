package com.danidev.composelearn.feature.clientes.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun ClienteDetailRoute(
    clienteId: Long,
    onBackClick: () -> Unit,
    viewModel: ClienteDetailViewModel = hiltViewModel()
) {
    LaunchedEffect(clienteId) {
        viewModel.observeCliente(clienteId)
    }

    val uiState by viewModel.uiState.collectAsState()

    ClienteDetailScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onAssignPuesto = viewModel::assignPuesto,
        onRemovePuesto = viewModel::removePuesto
    )
}