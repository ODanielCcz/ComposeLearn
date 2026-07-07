package com.danidev.composelearn.feature.clientes.domain.model

data class Cliente(
    val id: Long,
    val nombre: String,
    val telefono: String,
    val correo: String?,
    val fechaNacimiento: String?,
    val direccion: String,
    val observaciones: String?,
    val estatus: ClienteEstatus
)