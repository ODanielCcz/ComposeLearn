package com.danidev.composelearn.feature.clientes.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
fun ClienteDetailScreen(
    uiState: ClienteDetailUiState,
    onBackClick: () -> Unit,
    onAssignPuesto: (Long) -> Unit,
    onRemovePuesto: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var showAssignDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalle cliente") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            uiState.cliente?.let { cliente ->
                item {
                    ClienteInfoCard(cliente = cliente)
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Puestos asignados",
                            style = MaterialTheme.typography.titleMedium
                        )

                        OutlinedButton(
                            onClick = { showAssignDialog = true }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null
                            )
                            Text("Asignar")
                        }
                    }
                }

                if (uiState.puestosAsignados.isEmpty()) {
                    item {
                        Text(
                            text = "Sin puestos asignados",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    items(
                        items = uiState.puestosAsignados,
                        key = { puesto -> puesto.id }
                    ) { puesto ->
                        PuestoAsignadoCard(
                            puesto = puesto,
                            onRemoveClick = {
                                onRemovePuesto(puesto.id)
                            }
                        )
                    }
                }
            } ?: item {
                Text("Cliente no encontrado")
            }
        }
    }

    if (showAssignDialog) {
        AssignPuestoDialog(
            puestosDisponibles = uiState.puestosDisponibles,
            onDismiss = { showAssignDialog = false },
            onAssign = { puesto ->
                onAssignPuesto(puesto.id)
                showAssignDialog = false
            }
        )
    }
}

@Composable
private fun ClienteInfoCard(
    cliente: ClienteUiModel
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = cliente.nombre,
                style = MaterialTheme.typography.titleLarge
            )

            AssistChip(
                onClick = {},
                label = { Text(cliente.estatus.name) }
            )

            Text("Teléfono: ${cliente.telefono}")

            cliente.correo?.let {
                Text("Correo: $it")
            }

            cliente.fechaNacimiento?.let {
                Text("Fecha nacimiento: $it")
            }

            Text("Dirección: ${cliente.direccion}")

            cliente.observaciones?.let {
                Text("Observaciones: $it")
            }
        }
    }
}

@Composable
private fun PuestoAsignadoCard(
    puesto: ClientePuestoUiModel,
    onRemoveClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = puesto.nombre,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = puesto.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            TextButton(onClick = onRemoveClick) {
                Text("Quitar")
            }
        }
    }
}

@Composable
private fun AssignPuestoDialog(
    puestosDisponibles: List<ClientePuestoUiModel>,
    onDismiss: () -> Unit,
    onAssign: (ClientePuestoUiModel) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Asignar puesto") },
        text = {
            if (puestosDisponibles.isEmpty()) {
                Text("No hay puestos disponibles")
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = puestosDisponibles,
                        key = { puesto -> puesto.id }
                    ) { puesto ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = puesto.nombre,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = puesto.descripcion,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }

                                TextButton(
                                    onClick = { onAssign(puesto) }
                                ) {
                                    Text("Asignar")
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}