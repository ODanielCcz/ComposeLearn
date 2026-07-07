package com.danidev.composelearn.feature.clientes.data.mapper

import com.danidev.composelearn.feature.clientes.data.local.ClienteEntity
import com.danidev.composelearn.feature.clientes.domain.model.Cliente
import com.danidev.composelearn.feature.clientes.domain.model.ClienteEstatus

fun ClienteEntity.toDomain() = Cliente(
    id = id,
    nombre = nombre,
    telefono = telefono,
    correo = correo,
    fechaNacimiento = fechaNacimiento,
    direccion = direccion,
    observaciones = observaciones,
    estatus = runCatching {
        ClienteEstatus.valueOf(estatus)
    }.getOrDefault(ClienteEstatus.ACTIVO)
)

fun Cliente.toEntity() = ClienteEntity(
    id = id,
    nombre = nombre.trim(),
    telefono = telefono.trim(),
    correo = correo?.trim()?.takeIf { it.isNotBlank() },
    fechaNacimiento = fechaNacimiento?.trim()?.takeIf { it.isNotBlank() },
    direccion = direccion.trim(),
    observaciones = observaciones?.trim()?.takeIf { it.isNotBlank() },
    estatus = estatus.name
)