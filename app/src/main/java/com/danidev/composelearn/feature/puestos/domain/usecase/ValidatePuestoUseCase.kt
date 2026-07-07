package com.danidev.composelearn.feature.puestos.domain.usecase

import javax.inject.Inject

class ValidatePuestoUseCase @Inject constructor() {

    operator fun invoke(nombre: String, descripcion: String): Boolean {
        return nombre.isValidName() && descripcion.isValidDescription()
    }

    private fun String.isValidName(): Boolean {
        val value = trim()

        return value.length >= 3 &&
                value.any { it.isLetter() } &&
                value.all { it.isLetterOrDigit() || it.isWhitespace() }
    }

    private fun String.isValidDescription(): Boolean {
        return trim().any { it.isLetterOrDigit() }
    }
}