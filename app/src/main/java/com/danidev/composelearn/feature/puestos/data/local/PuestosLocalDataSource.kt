package com.danidev.composelearn.feature.puestos.data.local

import javax.inject.Inject

class PuestosLocalDataSource @Inject constructor(
    private val dao: PuestosDao
) {
    fun observePuestos() = dao.observePuestos()

    suspend fun insertPuesto(puesto: PuestoEntity) {
        dao.insertPuesto(puesto)
    }

    suspend fun updatePuesto(puesto: PuestoEntity) {
        dao.updatePuesto(puesto)
    }

    suspend fun deletePuesto(id: Long) {
        dao.deletePuesto(id)
    }
}