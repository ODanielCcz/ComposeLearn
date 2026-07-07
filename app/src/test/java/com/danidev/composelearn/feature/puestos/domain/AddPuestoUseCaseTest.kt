package com.danidev.composelearn.feature.puestos.domain

import com.danidev.composelearn.feature.puestos.domain.usecase.AddPuestoUseCase
import com.danidev.composelearn.feature.puestos.domain.usecase.ObservePuestosUseCase
import com.danidev.composelearn.feature.puestos.domain.usecase.ValidatePuestoUseCase
import org.junit.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class AddPuestoUseCaseTest {

    private val repository = FakePuestosRepository()
    private val addPuestoUseCase = AddPuestoUseCase(repository, ValidatePuestoUseCase())
    private val observePuestosUseCase = ObservePuestosUseCase(repository)

    @Test
    fun addPuesto_guardaPuestoEnRepository() = runTest {
        addPuestoUseCase(
            nombre = "Gerente",
            descripcion = "Administra personal"
        )

        val puestos = observePuestosUseCase().first()

        assertEquals(1, puestos.size)
        assertEquals("Gerente", puestos[0].nombre)
        assertEquals("Administra personal", puestos[0].descripcion)
    }
}