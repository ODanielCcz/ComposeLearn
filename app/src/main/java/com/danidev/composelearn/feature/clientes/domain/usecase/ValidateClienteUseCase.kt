package com.danidev.composelearn.feature.clientes.domain.usecase

import javax.inject.Inject

class ValidateClienteUseCase @Inject constructor() {

    operator fun invoke(
        nombre: String,
        telefono: String,
        direccion: String,
        correo: String?
    ): Boolean {
        return nombre.isValidName() &&
                telefono.isValidPhone() &&
                direccion.isValidAddress() &&
                correo.isValidOptionalEmail()
    }

    private fun String.isValidName(): Boolean {
        val value = trim()

        return value.length >= 3 &&
                value.any { it.isLetter() } &&
                value.all { it.isLetter() || it.isWhitespace() }
    }

    private fun String.isValidPhone(): Boolean {
        val value = trim()

        return value.length >= 7 &&
                value.all { it.isDigit() || it == ' ' || it == '-' || it == '+' }
    }

    private fun String.isValidAddress(): Boolean {
        return trim().length >= 5
    }

    private fun String?.isValidOptionalEmail(): Boolean {
        val value = this?.trim().orEmpty()

        if (value.isBlank()) {
            return true
        }

        return value.contains("@") && value.contains(".")
    }
}