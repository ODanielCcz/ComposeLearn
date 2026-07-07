package com.danidev.composelearn.feature.clientes.presentation

import com.danidev.composelearn.feature.clientes.presentation.components.DeactivateClienteDialog
import com.danidev.composelearn.feature.clientes.presentation.components.ClienteFormDialog
import com.danidev.composelearn.feature.clientes.presentation.components.ClienteCard
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientesScreen(
    uiState: ClientesUiState,
    onClienteClick: (ClienteUiModel) -> Unit,
    onSearchChange: (String) -> Unit,
    onAddCliente: (String, String, String?, String?, String, String?) -> Unit,
    onEditCliente: (ClienteUiModel) -> Unit,
    onDeactivateCliente: (ClienteUiModel) -> Unit,
    isClienteValid: (String, String, String, String?) -> Boolean,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var clienteToEdit by remember { mutableStateOf<ClienteUiModel?>(null) }
    var clienteToDeactivate by remember { mutableStateOf<ClienteUiModel?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Clientes") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar cliente"
                )
            }
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onSearchChange,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null
                    )
                },
                label = { Text(text = "Buscar cliente") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 88.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = uiState.filteredClientes,
                    key = { cliente -> cliente.id }
                ) { cliente ->
                    ClienteCard(
                        cliente = cliente,
                        onClick = { onClienteClick(cliente) },
                        onEditClick = { clienteToEdit = cliente },
                        onDeactivateClick = { clienteToDeactivate = cliente }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        ClienteFormDialog(
            title = "Agregar cliente",
            cliente = null,
            onDismiss = { showAddDialog = false },
            onConfirmNew = { nombre, telefono, correo, fechaNacimiento, direccion, observaciones ->
                onAddCliente(
                    nombre,
                    telefono,
                    correo,
                    fechaNacimiento,
                    direccion,
                    observaciones
                )
                showAddDialog = false
            },
            onConfirmEdit = {},
            isClienteValid = isClienteValid
        )
    }

    clienteToEdit?.let { cliente ->
        ClienteFormDialog(
            title = "Editar cliente",
            cliente = cliente,
            onDismiss = { clienteToEdit = null },
            onConfirmNew = { _, _, _, _, _, _ -> },
            onConfirmEdit = { updated ->
                onEditCliente(updated)
                clienteToEdit = null
            },
            isClienteValid = isClienteValid
        )
    }

    clienteToDeactivate?.let { cliente ->
        DeactivateClienteDialog(
            cliente = cliente,
            onDismiss = { clienteToDeactivate = null },
            onConfirm = {
                onDeactivateCliente(it)
                clienteToDeactivate = null
            }
        )
    }
}