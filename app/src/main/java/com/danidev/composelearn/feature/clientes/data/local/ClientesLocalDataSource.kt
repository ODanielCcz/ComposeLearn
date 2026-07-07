package com.danidev.composelearn.feature.clientes.data.local

import javax.inject.Inject

class ClientesLocalDataSource @Inject constructor(
    private val dao: ClientesDao
) {

    fun observeClientes() = dao.observeClientes()

    fun observeClienteById(id: Long) = dao.observeClienteById(id)

    fun observePuestoIdsByCliente(clienteId: Long) =
        dao.observePuestoIdsByCliente(clienteId)

    suspend fun insertCliente(cliente: ClienteEntity): Long =
        dao.insertCliente(cliente)

    suspend fun updateCliente(cliente: ClienteEntity) {
        dao.updateCliente(cliente)
    }

    suspend fun deactivateCliente(id: Long) {
        dao.deactivateCliente(id)
    }

    suspend fun assignPuestoToCliente(clienteId: Long, puestoId: Long) {
        dao.assignPuestoToCliente(
            ClientePuestoCrossRef(
                clienteId = clienteId,
                puestoId = puestoId
            )
        )
    }

    suspend fun removePuestoFromCliente(clienteId: Long, puestoId: Long) {
        dao.removePuestoFromCliente(clienteId, puestoId)
    }
}