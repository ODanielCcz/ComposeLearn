package com.danidev.composelearn.feature.clientes.domain.usecase

import com.danidev.composelearn.feature.clientes.domain.repository.ClientesRepository
import javax.inject.Inject

class AssignPuestoToClienteUseCase @Inject constructor(
    private val repository: ClientesRepository
) {
    suspend operator fun invoke(clienteId: Long, puestoId: Long) {
        repository.assignPuestoToCliente(clienteId, puestoId)
    }
}