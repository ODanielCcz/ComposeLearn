package com.danidev.composelearn.feature.clientes.data.repository

import com.danidev.composelearn.feature.clientes.data.local.ClienteEntity
import com.danidev.composelearn.feature.clientes.data.local.ClientesLocalDataSource
import com.danidev.composelearn.feature.clientes.data.mapper.toDomain
import com.danidev.composelearn.feature.clientes.data.mapper.toEntity
import com.danidev.composelearn.feature.clientes.domain.model.Cliente
import com.danidev.composelearn.feature.clientes.domain.repository.ClientesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ClientesRepositoryImpl @Inject constructor(
    private val localDataSource: ClientesLocalDataSource
) : ClientesRepository {

    override fun observeClientes(): Flow<List<Cliente>> {
        return localDataSource.observeClientes()
            .map { entities -> entities.map { it.toDomain() } }
    }

    override fun observeClienteById(id: Long): Flow<Cliente?> {
        return localDataSource.observeClienteById(id)
            .map { entity -> entity?.toDomain() }
    }

    override fun observePuestoIdsByCliente(clienteId: Long): Flow<List<Long>> {
        return localDataSource.observePuestoIdsByCliente(clienteId)
    }

    override suspend fun addCliente(
        nombre: String,
        telefono: String,
        correo: String?,
        fechaNacimiento: String?,
        direccion: String,
        observaciones: String?
    ) {
        localDataSource.insertCliente(
            ClienteEntity(
                nombre = nombre.trim(),
                telefono = telefono.trim(),
                correo = correo?.trim()?.takeIf { it.isNotBlank() },
                fechaNacimiento = fechaNacimiento?.trim()?.takeIf { it.isNotBlank() },
                direccion = direccion.trim(),
                observaciones = observaciones?.trim()?.takeIf { it.isNotBlank() }
            )
        )
    }

    override suspend fun updateCliente(cliente: Cliente) {
        localDataSource.updateCliente(cliente.toEntity())
    }

    override suspend fun deactivateCliente(id: Long) {
        localDataSource.deactivateCliente(id)
    }

    override suspend fun assignPuestoToCliente(clienteId: Long, puestoId: Long) {
        localDataSource.assignPuestoToCliente(clienteId, puestoId)
    }

    override suspend fun removePuestoFromCliente(clienteId: Long, puestoId: Long) {
        localDataSource.removePuestoFromCliente(clienteId, puestoId)
    }
}