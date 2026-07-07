package com.danidev.composelearn.feature.clientes.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.danidev.composelearn.feature.clientes.presentation.ClienteUiModel

@Composable
fun DeactivateClienteDialog(
    cliente: ClienteUiModel,
    onDismiss: () -> Unit,
    onConfirm: (ClienteUiModel) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Desactivar cliente") },
        text = { Text("¿Desactivar ${cliente.nombre}?") },
        confirmButton = {
            TextButton(onClick = { onConfirm(cliente) }) {
                Text("Desactivar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}