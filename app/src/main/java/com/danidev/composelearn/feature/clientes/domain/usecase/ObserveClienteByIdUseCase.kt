package com.danidev.composelearn.feature.clientes.domain.usecase

import com.danidev.composelearn.feature.clientes.domain.repository.ClientesRepository
import javax.inject.Inject

class ObserveClienteByIdUseCase @Inject constructor(
    private val repository: ClientesRepository
) {
    operator fun invoke(id: Long) = repository.observeClienteById(id)
}