package com.danidev.composelearn.feature.puestos.domain.usecase

import com.danidev.composelearn.feature.puestos.domain.repository.PuestosRepository
import javax.inject.Inject

class AddPuestoUseCase @Inject constructor(
    private val repository: PuestosRepository,
    private val validatePuestoUseCase: ValidatePuestoUseCase
) {
    suspend operator fun invoke(nombre: String, descripcion: String) {
        if (!validatePuestoUseCase(nombre, descripcion)) return

        repository.addPuesto(nombre, descripcion)
    }
}