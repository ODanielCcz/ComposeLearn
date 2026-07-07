package com.danidev.composelearn.feature.puestos.data.mapper

import com.danidev.composelearn.feature.puestos.data.local.PuestoEntity
import com.danidev.composelearn.feature.puestos.domain.model.Puesto

fun PuestoEntity.toDomain() = Puesto(
    id = id,
    nombre = nombre,
    descripcion = descripcion
)

fun Puesto.toEntity() = PuestoEntity(
    id = id,
    nombre = nombre,
    descripcion = descripcion
)