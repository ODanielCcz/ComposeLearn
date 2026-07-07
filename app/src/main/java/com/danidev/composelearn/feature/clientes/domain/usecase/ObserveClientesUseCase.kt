package com.danidev.composelearn.feature.clientes.domain.usecase

import com.danidev.composelearn.feature.clientes.domain.repository.ClientesRepository
import javax.inject.Inject

class ObserveClientesUseCase @Inject constructor(
    private val repository: ClientesRepository
) {
    operator fun invoke() = repository.observeClientes()
}