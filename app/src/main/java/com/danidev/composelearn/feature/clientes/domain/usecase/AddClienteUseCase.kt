package com.danidev.composelearn.feature.clientes.domain.usecase

import com.danidev.composelearn.feature.clientes.domain.repository.ClientesRepository
import javax.inject.Inject

class AddClienteUseCase @Inject constructor(
    private val repository: ClientesRepository,
    private val validateClienteUseCase: ValidateClienteUseCase
) {
    suspend operator fun invoke(
        nombre: String,
        telefono: String,
        correo: String?,
        fechaNacimiento: String?,
        direccion: String,
        observaciones: String?
    ) {
        if (!validateClienteUseCase(nombre, telefono, direccion, correo)) {
            return
        }

        repository.addCliente(
            nombre = nombre,
            telefono = telefono,
            correo = correo,
            fechaNacimiento = fechaNacimiento,
            direccion = direccion,
            observaciones = observaciones
        )
    }
}