package com.danidev.composelearn.feature.puestos.domain.usecase

import com.danidev.composelearn.feature.puestos.domain.repository.PuestosRepository
import javax.inject.Inject

class UpdatePuestoUseCase @Inject constructor(
    private val repository: PuestosRepository,
    private val validatePuestoUseCase: ValidatePuestoUseCase
) {
    suspend operator fun invoke(id: Long, nombre: String, descripcion: String) {
        if (!validatePuestoUseCase(nombre, descripcion)) return

        repository.updatePuesto(id, nombre, descripcion)
    }
}