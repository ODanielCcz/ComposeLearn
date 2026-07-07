package com.danidev.composelearn.feature.puestos.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danidev.composelearn.feature.puestos.domain.model.Puesto
import com.danidev.composelearn.feature.puestos.domain.usecase.AddPuestoUseCase
import com.danidev.composelearn.feature.puestos.domain.usecase.DeletePuestoUseCase
import com.danidev.composelearn.feature.puestos.domain.usecase.ObservePuestosUseCase
import com.danidev.composelearn.feature.puestos.domain.usecase.UpdatePuestoUseCase
import com.danidev.composelearn.feature.puestos.domain.usecase.ValidatePuestoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PuestosViewModel @Inject constructor(
    private val observePuestosUseCase: ObservePuestosUseCase,
    private val addPuestoUseCase: AddPuestoUseCase,
    private val updatePuestoUseCase: UpdatePuestoUseCase,
    private val deletePuestoUseCase: DeletePuestoUseCase,
    private val validatePuestoUseCase: ValidatePuestoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PuestosUiState())
    val uiState: StateFlow<PuestosUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observePuestosUseCase().collect { puestos ->
                _uiState.update { state ->
                    state.copy(puestos = puestos.map { it.toUiModel() })
                }
            }
        }
    }

    fun onSearchChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun addPuesto(nombre: String, descripcion: String) {
        viewModelScope.launch {
            addPuestoUseCase(nombre, descripcion)
        }
    }

    fun updatePuesto(id: Long, nombre: String, descripcion: String) {
        viewModelScope.launch {
            updatePuestoUseCase(id, nombre, descripcion)
        }
    }

    fun deletePuesto(id: Long) {
        viewModelScope.launch {
            deletePuestoUseCase(id)
        }
    }

    fun isPuestoValid(nombre: String, descripcion: String): Boolean {
        return validatePuestoUseCase(nombre, descripcion)
    }

    private fun Puesto.toUiModel() = PuestoUiModel(
        id = id,
        nombre = nombre,
        descripcion = descripcion
    )
}