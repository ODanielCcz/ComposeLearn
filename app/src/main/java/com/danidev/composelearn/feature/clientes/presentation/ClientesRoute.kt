package com.danidev.composelearn.feature.clientes.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun ClientesRoute(
    onClienteClick: (Long) -> Unit,
    viewModel: ClientesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    ClientesScreen(
        uiState = uiState,
        onClienteClick = { cliente ->
            onClienteClick(cliente.id)
        },
        onSearchChange = viewModel::onSearchChange,
        onAddCliente = viewModel::addCliente,
        onEditCliente = viewModel::updateCliente,
        onDeactivateCliente = { cliente ->
            viewModel.deactivateCliente(cliente.id)
        },
        isClienteValid = viewModel::isClienteValid
    )
}