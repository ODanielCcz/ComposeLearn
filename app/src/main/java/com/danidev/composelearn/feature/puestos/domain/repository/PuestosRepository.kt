package com.danidev.composelearn.feature.puestos.domain.repository

import com.danidev.composelearn.feature.puestos.domain.model.Puesto
import kotlinx.coroutines.flow.Flow

interface PuestosRepository {
    fun observePuestos(): Flow<List<Puesto>>
    suspend fun addPuesto(nombre: String, descripcion: String)
    suspend fun updatePuesto(id: Long, nombre: String, descripcion: String)
    suspend fun deletePuesto(id: Long)
}