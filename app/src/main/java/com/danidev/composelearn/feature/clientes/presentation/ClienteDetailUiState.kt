package com.danidev.composelearn.feature.clientes.presentation

data class ClienteDetailUiState(
    val cliente: ClienteUiModel? = null,
    val puestosAsignados: List<ClientePuestoUiModel> = emptyList(),
    val puestosDisponibles: List<ClientePuestoUiModel> = emptyList()
)

data class ClientePuestoUiModel(
    val id: Long,
    val nombre: String,
    val descripcion: String
)