package com.danidev.composelearn.feature.clientes.presentation.components

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
import com.danidev.composelearn.feature.clientes.presentation.ClienteUiModel

@Composable
fun ClienteFormDialog(
    title: String,
    cliente: ClienteUiModel?,
    onDismiss: () -> Unit,
    onConfirmNew: (String, String, String?, String?, String, String?) -> Unit,
    onConfirmEdit: (ClienteUiModel) -> Unit,
    isClienteValid: (String, String, String, String?) -> Boolean
) {
    var nombre by remember { mutableStateOf(cliente?.nombre.orEmpty()) }
    var telefono by remember { mutableStateOf(cliente?.telefono.orEmpty()) }
    var correo by remember { mutableStateOf(cliente?.correo.orEmpty()) }
    var fechaNacimiento by remember { mutableStateOf(cliente?.fechaNacimiento.orEmpty()) }
    var direccion by remember { mutableStateOf(cliente?.direccion.orEmpty()) }
    var observaciones by remember { mutableStateOf(cliente?.observaciones.orEmpty()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title) },
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
                    value = telefono,
                    onValueChange = { telefono = it },
                    label = { Text("Teléfono") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = correo,
                    onValueChange = { correo = it },
                    label = { Text("Correo") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = fechaNacimiento,
                    onValueChange = { fechaNacimiento = it },
                    label = { Text("Fecha nacimiento") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    label = { Text("Dirección") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = observaciones,
                    onValueChange = { observaciones = it },
                    label = { Text("Observaciones") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                enabled = isClienteValid(
                    nombre,
                    telefono,
                    direccion,
                    correo.takeIf { it.isNotBlank() }
                ),
                onClick = {
                    if (cliente == null) {
                        onConfirmNew(
                            nombre,
                            telefono,
                            correo.takeIf { it.isNotBlank() },
                            fechaNacimiento.takeIf { it.isNotBlank() },
                            direccion,
                            observaciones.takeIf { it.isNotBlank() }
                        )
                    } else {
                        onConfirmEdit(
                            cliente.copy(
                                nombre = nombre,
                                telefono = telefono,
                                correo = correo.takeIf { it.isNotBlank() },
                                fechaNacimiento = fechaNacimiento.takeIf { it.isNotBlank() },
                                direccion = direccion,
                                observaciones = observaciones.takeIf { it.isNotBlank() }
                            )
                        )
                    }
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