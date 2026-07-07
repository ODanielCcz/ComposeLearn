package com.danidev.composelearn.feature.puestos.presentation

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
import com.danidev.composelearn.feature.puestos.presentation.components.AddPuestoDialog
import com.danidev.composelearn.feature.puestos.presentation.components.DeletePuestoDialog
import com.danidev.composelearn.feature.puestos.presentation.components.EditPuestoDialog
import com.danidev.composelearn.feature.puestos.presentation.components.PuestoCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuestosScreen(
    uiState: PuestosUiState,
    onSearchChange: (String) -> Unit,
    onAddPuesto: (String, String) -> Unit,
    onEditPuesto: (PuestoUiModel) -> Unit,
    onDeletePuesto: (PuestoUiModel) -> Unit,
    isPuestoValid: (String, String) -> Boolean,
    modifier: Modifier = Modifier
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var puestoToEdit by remember { mutableStateOf<PuestoUiModel?>(null) }
    var puestoToDelete by remember { mutableStateOf<PuestoUiModel?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Puestos") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddDialog = true }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Agregar puesto"
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
                label = { Text(text = "Buscar puesto") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 88.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(
                    items = uiState.filteredPuestos,
                    key = { puesto -> puesto.id }
                ) { puesto ->
                    PuestoCard(
                        puesto = puesto,
                        onEditClick = { puestoToEdit = puesto },
                        onDeleteClick = { puestoToDelete = puesto }
                    )
                }
            }
        }
    }

    puestoToEdit?.let { puesto ->
        EditPuestoDialog(
            puesto = puesto,
            onDismiss = { puestoToEdit = null },
            onConfirm = { updated ->
                onEditPuesto(updated)
                puestoToEdit = null
            },
            isPuestoValid = isPuestoValid
        )
    }

    puestoToDelete?.let { puesto ->
        DeletePuestoDialog(
            puesto = puesto,
            onDismiss = { puestoToDelete = null },
            onConfirm = {
                onDeletePuesto(it)
                puestoToDelete = null
            }
        )
    }

    if (showAddDialog) {
        AddPuestoDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { nombre, descripcion ->
                onAddPuesto(nombre, descripcion)
                showAddDialog = false
            },
            isPuestoValid = isPuestoValid
        )
    }
}