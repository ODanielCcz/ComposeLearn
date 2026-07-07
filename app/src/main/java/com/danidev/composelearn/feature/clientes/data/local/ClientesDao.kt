package com.danidev.composelearn.feature.clientes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ClientesDao {

    @Query("SELECT * FROM clientes ORDER BY nombre ASC")
    fun observeClientes(): Flow<List<ClienteEntity>>

    @Query("SELECT * FROM clientes WHERE id = :id")
    fun observeClienteById(id: Long): Flow<ClienteEntity?>

    @Insert
    suspend fun insertCliente(cliente: ClienteEntity): Long

    @Update
    suspend fun updateCliente(cliente: ClienteEntity)

    @Query("UPDATE clientes SET estatus = 'INACTIVO' WHERE id = :id")
    suspend fun deactivateCliente(id: Long)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun assignPuestoToCliente(crossRef: ClientePuestoCrossRef)

    @Query(
    """
        DELETE FROM cliente_puesto
        WHERE cliente_id = :clienteId AND puesto_id = :puestoId
        """
    )
    suspend fun removePuestoFromCliente(clienteId: Long, puestoId: Long)

    @Query(
        """
        SELECT puesto_id FROM cliente_puesto
        WHERE cliente_id = :clienteId
        """
    )
    fun observePuestoIdsByCliente(clienteId: Long): Flow<List<Long>>

}