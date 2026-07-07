package com.danidev.composelearn.feature.puestos.domain

import com.danidev.composelearn.feature.puestos.domain.model.Puesto
import com.danidev.composelearn.feature.puestos.domain.repository.PuestosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakePuestosRepository : PuestosRepository {

    private val puestos = MutableStateFlow<List<Puesto>>(emptyList())

    override fun observePuestos(): Flow<List<Puesto>> {
        return puestos
    }

    override suspend fun addPuesto(nombre: String, descripcion: String) {
        val current = puestos.value
        val newPuesto = Puesto(
            id = (current.size + 1).toLong(),
            nombre = nombre,
            descripcion = descripcion
        )

        puestos.value = current + newPuesto
    }

    override suspend fun updatePuesto(
        id: Long,
        nombre: String,
        descripcion: String
    ) {
        puestos.value = puestos.value.map { puesto ->
            if (puesto.id == id) {
                puesto.copy(
                    nombre = nombre,
                    descripcion = descripcion
                )
            } else {
                puesto
            }
        }
    }

    override suspend fun deletePuesto(id: Long) {
        puestos.value = puestos.value.filterNot { puesto ->
            puesto.id == id
        }
    }

}