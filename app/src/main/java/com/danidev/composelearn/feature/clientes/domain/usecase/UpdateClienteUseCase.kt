package com.danidev.composelearn.feature.clientes.domain.usecase

import com.danidev.composelearn.feature.clientes.domain.repository.ClientesRepository
import com.danidev.composelearn.feature.clientes.domain.model.Cliente
import javax.inject.Inject

class UpdateClienteUseCase @Inject constructor(
    private val repository: ClientesRepository,
    private val validateClienteUseCase: ValidateClienteUseCase
) {
    suspend operator fun invoke(cliente: Cliente) {
        if (
            !validateClienteUseCase(
                nombre = cliente.nombre,
                telefono = cliente.telefono,
                direccion = cliente.direccion,
                correo = cliente.correo
            )
        ) {
            return
        }

        repository.updateCliente(cliente)
    }
}