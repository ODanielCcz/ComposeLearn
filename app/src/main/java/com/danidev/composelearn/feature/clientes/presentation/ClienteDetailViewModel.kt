package com.danidev.composelearn.feature.clientes.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danidev.composelearn.feature.clientes.domain.model.Cliente
import com.danidev.composelearn.feature.clientes.domain.usecase.AssignPuestoToClienteUseCase
import com.danidev.composelearn.feature.clientes.domain.usecase.ObserveClienteByIdUseCase
import com.danidev.composelearn.feature.clientes.domain.usecase.ObservePuestoIdsByClienteUseCase
import com.danidev.composelearn.feature.clientes.domain.usecase.RemovePuestoFromClienteUseCase
import com.danidev.composelearn.feature.puestos.domain.model.Puesto
import com.danidev.composelearn.feature.puestos.domain.usecase.ObservePuestosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClienteDetailViewModel @Inject constructor(
    private val observeClienteByIdUseCase: ObserveClienteByIdUseCase,
    private val observePuestosUseCase: ObservePuestosUseCase,
    private val observePuestoIdsByClienteUseCase: ObservePuestoIdsByClienteUseCase,
    private val assignPuestoToClienteUseCase: AssignPuestoToClienteUseCase,
    private val removePuestoFromClienteUseCase: RemovePuestoFromClienteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClienteDetailUiState())
    val uiState: StateFlow<ClienteDetailUiState> = _uiState.asStateFlow()

    private var currentClienteId: Long? = null
    private var observeJob: Job? = null

    fun observeCliente(clienteId: Long) {
        if (currentClienteId == clienteId) {
            return
        }

        currentClienteId = clienteId
        observeJob?.cancel()

        observeJob = viewModelScope.launch {
            combine(
                observeClienteByIdUseCase(clienteId),
                observePuestosUseCase(),
                observePuestoIdsByClienteUseCase(clienteId)
            ) { cliente, puestos, puestosIds ->
                val asignados = puestos
                    .filter { puesto -> puesto.id in puestosIds }
                    .map { puesto -> puesto.toClientePuestoUiModel() }

                val disponibles = puestos
                    .filterNot { puesto -> puesto.id in puestosIds }
                    .map { puesto -> puesto.toClientePuestoUiModel() }

                ClienteDetailUiState(
                    cliente = cliente?.toUiModel(),
                    puestosAsignados = asignados,
                    puestosDisponibles = disponibles
                )
            }.collect { state ->
                _uiState.update { state }
            }
        }
    }

    fun assignPuesto(puestoId: Long) {
        val clienteId = currentClienteId ?: return

        viewModelScope.launch {
            assignPuestoToClienteUseCase(
                clienteId = clienteId,
                puestoId = puestoId
            )
        }
    }

    fun removePuesto(puestoId: Long) {
        val clienteId = currentClienteId ?: return

        viewModelScope.launch {
            removePuestoFromClienteUseCase(
                clienteId = clienteId,
                puestoId = puestoId
            )
        }
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

    private fun Puesto.toClientePuestoUiModel() = ClientePuestoUiModel(
        id = id,
        nombre = nombre,
        descripcion = descripcion
    )
}