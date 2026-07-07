package com.danidev.composelearn.feature.puestos.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danidev.composelearn.feature.puestos.presentation.PuestoUiModel

@Composable
fun EditPuestoDialog(
    puesto: PuestoUiModel,
    onDismiss: () -> Unit,
    onConfirm: (PuestoUiModel) -> Unit,
    isPuestoValid: (String, String) -> Boolean
) {
    var nombre by remember { mutableStateOf(puesto.nombre) }
    var descripcion by remember { mutableStateOf(puesto.descripcion) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Editar puesto") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = isPuestoValid(nombre, descripcion),
                onClick = {
                    onConfirm(
                        puesto.copy(
                            nombre = nombre,
                            descripcion = descripcion
                        )
                    )
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}