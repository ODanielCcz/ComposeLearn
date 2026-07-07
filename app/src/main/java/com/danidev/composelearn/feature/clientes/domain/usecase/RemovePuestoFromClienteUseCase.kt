package com.danidev.composelearn.feature.clientes.domain.usecase

import com.danidev.composelearn.feature.clientes.domain.repository.ClientesRepository
import javax.inject.Inject

class RemovePuestoFromClienteUseCase @Inject constructor(
    private val repository: ClientesRepository
) {
    suspend operator fun invoke(clienteId: Long, puestoId: Long) {
        repository.removePuestoFromCliente(clienteId, puestoId)
    }
}