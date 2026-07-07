package com.danidev.composelearn.feature.puestos.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PuestosDao {
    @Query("SELECT * FROM puestos ORDER BY nombre ASC")
    fun observePuestos(): Flow<List<PuestoEntity>>

    @Insert
    suspend fun insertPuesto(puesto: PuestoEntity)

    @Update
    suspend fun updatePuesto(puesto: PuestoEntity)

    @Query("DELETE FROM puestos WHERE id = :id")
    suspend fun deletePuesto(id: Long)
}