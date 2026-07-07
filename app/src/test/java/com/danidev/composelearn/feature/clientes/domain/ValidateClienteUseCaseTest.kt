package com.danidev.composelearn.feature.clientes.domain

import com.danidev.composelearn.feature.clientes.domain.usecase.ValidateClienteUseCase
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidateClienteUseCaseTest {

    private val validateClienteUseCase = ValidateClienteUseCase()

    @Test
    fun invoke_conDatosValidos_retornaTrue() {
        val result = validateClienteUseCase(
            nombre = "Empresa Centro",
            telefono = "5512345678",
            direccion = "Av Centro 123",
            correo = "contacto@empresa.com"
        )

        assertTrue(result)
    }

    @Test
    fun invoke_conCorreoVacio_retornaTrue() {
        val result = validateClienteUseCase(
            nombre = "Empresa Centro",
            telefono = "5512345678",
            direccion = "Av Centro 123",
            correo = ""
        )

        assertTrue(result)
    }

    @Test
    fun invoke_conNombreCorto_retornaFalse() {
        val result = validateClienteUseCase(
            nombre = "AB",
            telefono = "5512345678",
            direccion = "Av Centro 123",
            correo = "contacto@empresa.com"
        )

        assertFalse(result)
    }

    @Test
    fun invoke_conNombreSinLetras_retornaFalse() {
        val result = validateClienteUseCase(
            nombre = "12345",
            telefono = "5512345678",
            direccion = "Av Centro 123",
            correo = "contacto@empresa.com"
        )

        assertFalse(result)
    }

    @Test
    fun invoke_conTelefonoCorto_retornaFalse() {
        val result = validateClienteUseCase(
            nombre = "Empresa Centro",
            telefono = "123",
            direccion = "Av Centro 123",
            correo = "contacto@empresa.com"
        )

        assertFalse(result)
    }

    @Test
    fun invoke_conTelefonoConLetras_retornaFalse() {
        val result = validateClienteUseCase(
            nombre = "Empresa Centro",
            telefono = "55ABC123",
            direccion = "Av Centro 123",
            correo = "contacto@empresa.com"
        )

        assertFalse(result)
    }

    @Test
    fun invoke_conDireccionCorta_retornaFalse() {
        val result = validateClienteUseCase(
            nombre = "Empresa Centro",
            telefono = "5512345678",
            direccion = "Av",
            correo = "contacto@empresa.com"
        )

        assertFalse(result)
    }

    @Test
    fun invoke_conCorreoInvalido_retornaFalse() {
        val result = validateClienteUseCase(
            nombre = "Empresa Centro",
            telefono = "5512345678",
            direccion = "Av Centro 123",
            correo = "correo-invalido"
        )

        assertFalse(result)
    }

}