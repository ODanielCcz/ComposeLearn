package com.danidev.composelearn.feature.puestos.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.danidev.composelearn.feature.puestos.presentation.PuestoUiModel

@Composable
fun DeletePuestoDialog(
    puesto: PuestoUiModel,
    onDismiss: () -> Unit,
    onConfirm: (PuestoUiModel) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Eliminar puesto") },
        text = { Text("¿Eliminar ${puesto.nombre}?") },
        confirmButton = {
            TextButton(onClick = { onConfirm(puesto) }) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}