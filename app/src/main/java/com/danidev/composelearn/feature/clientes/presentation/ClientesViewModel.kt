package com.danidev.composelearn.feature.clientes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danidev.composelearn.feature.clientes.domain.model.Cliente
import com.danidev.composelearn.feature.clientes.domain.usecase.AddClienteUseCase
import com.danidev.composelearn.feature.clientes.domain.usecase.DeactivateClienteUseCase
import com.danidev.composelearn.feature.clientes.domain.usecase.ObserveClientesUseCase
import com.danidev.composelearn.feature.clientes.domain.usecase.UpdateClienteUseCase
import com.danidev.composelearn.feature.clientes.domain.usecase.ValidateClienteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientesViewModel @Inject constructor(
    private val observeClientesUseCase: ObserveClientesUseCase,
    private val addClienteUseCase: AddClienteUseCase,
    private val updateClienteUseCase: UpdateClienteUseCase,
    private val deactivateClienteUseCase: DeactivateClienteUseCase,
    private val validateClienteUseCase: ValidateClienteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientesUiState())
    val uiState: StateFlow<ClientesUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            observeClientesUseCase().collect { clientes ->
                _uiState.update { state ->
                    state.copy(
                        clientes = clientes.map { it.toUiModel() }
                    )
                }
            }
        }
    }

    fun onSearchChange(query: String) {
        _uiState.update { state ->
            state.copy(searchQuery = query)
        }
    }

    fun addCliente(
        nombre: String,
        telefono: String,
        correo: String?,
        fechaNacimiento: String?,
        direccion: String,
        observaciones: String?
    ) {
        viewModelScope.launch {
            addClienteUseCase(
                nombre = nombre,
                telefono = telefono,
                correo = correo,
                fechaNacimiento = fechaNacimiento,
                direccion = direccion,
                observaciones = observaciones
            )
        }
    }

    fun updateCliente(cliente: ClienteUiModel) {
        viewModelScope.launch {
            updateClienteUseCase(cliente.toDomain())
        }
    }

    fun deactivateCliente(id: Long) {
        viewModelScope.launch {
            deactivateClienteUseCase(id)
        }
    }

    fun isClienteValid(
        nombre: String,
        telefono: String,
        direccion: String,
        correo: String?
    ): Boolean {
        return validateClienteUseCase(
            nombre = nombre,
            telefono = telefono,
            direccion = direccion,
            correo = correo
        )
    }

    private fun Cliente.toUiModel() = ClienteUiModel(
        id = id,
        nombre = nombre,
        telefono = telefono,
        correo = correo,
        fechaNacimiento = fechaNacimiento,
        direccion = direccion,
        observaciones = observaciones,
        estatus = estatus
    )

    private fun ClienteUiModel.toDomain() = Cliente(
        id = id,
        nombre = nombre,
        telefono = telefono,
        correo = correo,
        fechaNacimiento = fechaNacimiento,
        direccion = direccion,
        observaciones = observaciones,
        estatus = estatus
    )
}