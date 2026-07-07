package com.danidev.composelearn.feature.puestos.domain

import com.danidev.composelearn.feature.puestos.domain.usecase.AddPuestoUseCase
import com.danidev.composelearn.feature.puestos.domain.usecase.ObservePuestosUseCase
import com.danidev.composelearn.feature.puestos.domain.usecase.ValidatePuestoUseCase
import org.junit.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ObservePuestosUseCaseTest {

    private val repository = FakePuestosRepository()
    private val addPuestoUseCase = AddPuestoUseCase(repository, ValidatePuestoUseCase())
    private val observePuestosUseCase = ObservePuestosUseCase(repository)

    @Test
    fun observePuestos_retornarPuestosGuardados() = runTest {
        addPuestoUseCase("Cajero", "Atiende caja")
        addPuestoUseCase("Supervisor", "Revisa operaciones")

        val puestos = observePuestosUseCase().first()

        assertEquals(2, puestos.size)
        assertEquals("Cajero", puestos[0].nombre)
        assertEquals("Supervisor", puestos[1].nombre)
    }
}