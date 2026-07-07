package com.danidev.composelearn.feature.clientes.domain.usecase

import com.danidev.composelearn.feature.clientes.domain.repository.ClientesRepository
import javax.inject.Inject

class DeactivateClienteUseCase @Inject constructor(
    private val repository: ClientesRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deactivateCliente(id)
    }
}