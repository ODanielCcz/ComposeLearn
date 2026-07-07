package com.danidev.composelearn.feature.puestos.domain.usecase

import com.danidev.composelearn.feature.puestos.domain.repository.PuestosRepository
import javax.inject.Inject

class ObservePuestosUseCase @Inject constructor(
    private val repository: PuestosRepository
) {
    operator fun invoke() = repository.observePuestos()
}