package com.danidev.composelearn.feature.puestos.data.repository

import com.danidev.composelearn.feature.puestos.data.local.PuestoEntity
import com.danidev.composelearn.feature.puestos.data.local.PuestosLocalDataSource
import com.danidev.composelearn.feature.puestos.data.mapper.toDomain
import com.danidev.composelearn.feature.puestos.domain.model.Puesto
import com.danidev.composelearn.feature.puestos.domain.repository.PuestosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PuestosRepositoryImpl @Inject constructor(
    private val localDataSource: PuestosLocalDataSource
) : PuestosRepository {
    override fun observePuestos(): Flow<List<Puesto>> {
        return localDataSource.observePuestos()
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun addPuesto(nombre: String, descripcion: String) {
        localDataSource.insertPuesto(
            PuestoEntity(
                nombre = nombre.trim(),
                descripcion = descripcion.trim()
            )
        )
    }

    override suspend fun updatePuesto(id: Long, nombre: String, descripcion: String) {
        localDataSource.updatePuesto(
            PuestoEntity(
                id = id,
                nombre = nombre.trim(),
                descripcion = descripcion.trim()
            )
        )
    }

    override suspend fun deletePuesto(id: Long) {
        localDataSource.deletePuesto(id)
    }
}