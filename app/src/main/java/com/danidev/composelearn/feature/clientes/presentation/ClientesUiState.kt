package com.danidev.composelearn.feature.clientes.presentation

import com.danidev.composelearn.feature.clientes.domain.model.ClienteEstatus

data class ClientesUiState(
    val searchQuery: String = "",
    val clientes: List<ClienteUiModel> = emptyList()
) {
    val filteredClientes: List<ClienteUiModel>
        get() = if (searchQuery.isBlank()) {
            clientes
        } else {
            clientes.filter { cliente ->
                cliente.nombre.contains(searchQuery, ignoreCase = true) ||
                        cliente.telefono.contains(searchQuery, ignoreCase = true) ||
                        cliente.direccion.contains(searchQuery, ignoreCase = true)
            }
        }
}

data class ClienteUiModel(
    val id: Long,
    val nombre: String,
    val telefono: String,
    val correo: String?,
    val fechaNacimiento: String?,
    val direccion: String,
    val observaciones: String?,
    val estatus: ClienteEstatus
)