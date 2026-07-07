package com.danidev.composelearn.feature.clientes.domain.repository

import com.danidev.composelearn.feature.clientes.domain.model.Cliente
import kotlinx.coroutines.flow.Flow

interface ClientesRepository {

    fun observeClientes(): Flow<List<Cliente>>

    fun observeClienteById(id: Long): Flow<Cliente?>

    fun observePuestoIdsByCliente(clienteId: Long): Flow<List<Long>>

    suspend fun addCliente(
        nombre: String,
        telefono: String,
        correo: String?,
        fechaNacimiento: String?,
        direccion: String,
        observaciones: String?
    )

    suspend fun updateCliente(cliente: Cliente)

    suspend fun deactivateCliente(id: Long)

    suspend fun assignPuestoToCliente(clienteId: Long, puestoId: Long)

    suspend fun removePuestoFromCliente(clienteId: Long, puestoId: Long)
}