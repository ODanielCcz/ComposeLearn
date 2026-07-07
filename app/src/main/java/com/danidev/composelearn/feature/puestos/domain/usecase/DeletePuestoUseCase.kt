package com.danidev.composelearn.feature.puestos.domain.usecase

import com.danidev.composelearn.feature.puestos.domain.repository.PuestosRepository
import javax.inject.Inject

class DeletePuestoUseCase @Inject constructor(
    private val repository: PuestosRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deletePuesto(id)
    }
}