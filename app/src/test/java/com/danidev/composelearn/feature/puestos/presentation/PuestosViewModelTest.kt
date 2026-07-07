package com.danidev.composelearn.feature.puestos.presentation

import com.danidev.composelearn.feature.puestos.domain.FakePuestosRepository
import com.danidev.composelearn.feature.puestos.domain.usecase.AddPuestoUseCase
import com.danidev.composelearn.feature.puestos.domain.usecase.DeletePuestoUseCase
import com.danidev.composelearn.feature.puestos.domain.usecase.ObservePuestosUseCase
import com.danidev.composelearn.feature.puestos.domain.usecase.UpdatePuestoUseCase
import com.danidev.composelearn.feature.puestos.domain.usecase.ValidatePuestoUseCase
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class PuestosViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun addPuesto_actualizaUiState() {
        val repository = FakePuestosRepository()
        val viewModel = createViewModel(repository)

        viewModel.addPuesto(
            nombre = "Gerente",
            descripcion = "Administra personal"
        )

        val state = viewModel.uiState.value

        assertEquals(1, state.puestos.size)
        assertEquals("Gerente", state.puestos[0].nombre)
        assertEquals("Administra personal", state.puestos[0].descripcion)
    }

    @Test
    fun onSearchChange_filtraPuestos() {
        val repository = FakePuestosRepository()
        val viewModel = createViewModel(repository)

        viewModel.addPuesto("Gerente", "Administra personal")
        viewModel.addPuesto("Cajero", "Atiende caja")

        viewModel.onSearchChange("gere")

        val state = viewModel.uiState.value

        assertEquals("gere", state.searchQuery)
        assertEquals(1, state.filteredPuestos.size)
        assertEquals("Gerente", state.filteredPuestos[0].nombre)
    }

    @Test
    fun updatePuesto_actualizaUiState() {
        val repository = FakePuestosRepository()
        val viewModel = createViewModel(repository)

        viewModel.addPuesto("Cajero", "Atiende caja")
        viewModel.updatePuesto(1L, "Cajero senior", "Atiende caja principal")

        val state = viewModel.uiState.value

        assertEquals(1, state.puestos.size)
        assertEquals("Cajero senior", state.puestos[0].nombre)
        assertEquals("Atiende caja principal", state.puestos[0].descripcion)
    }

    @Test
    fun deletePuesto_eliminaPuestoDeUiState() {
        val repository = FakePuestosRepository()
        val viewModel = createViewModel(repository)

        viewModel.addPuesto("Cajero", "Atiende caja")
        viewModel.deletePuesto(1L)

        val state = viewModel.uiState.value

        assertEquals(0, state.puestos.size)
    }

    private fun createViewModel(
        repository: FakePuestosRepository
    ): PuestosViewModel {
        return PuestosViewModel(
            observePuestosUseCase = ObservePuestosUseCase(repository),
            addPuestoUseCase = AddPuestoUseCase(repository, ValidatePuestoUseCase()),
            updatePuestoUseCase = UpdatePuestoUseCase(repository, ValidatePuestoUseCase()),
            deletePuestoUseCase = DeletePuestoUseCase(repository),
            validatePuestoUseCase = ValidatePuestoUseCase()
        )
    }
}