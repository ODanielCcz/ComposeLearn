package com.danidev.composelearn.feature.puestos.presentation

data class PuestosUiState(
    val searchQuery: String = "",
    val puestos: List<PuestoUiModel> = emptyList()
) {
    val filteredPuestos: List<PuestoUiModel>
        get() = if (searchQuery.isBlank()) {
            puestos
        } else {
            puestos.filter {
                it.nombre.contains(searchQuery, true) ||
                        it.descripcion.contains(searchQuery, true)
            }
        }
}

data class PuestoUiModel(
    val id: Long,
    val nombre: String,
    val descripcion: String
)