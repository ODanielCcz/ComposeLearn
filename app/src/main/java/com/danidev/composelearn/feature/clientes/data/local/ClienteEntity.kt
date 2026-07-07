package com.danidev.composelearn.feature.clientes.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "clientes",
    indices = [
        Index(value = ["nombre"]),
        Index(value = ["telefono"])
    ]
)
data class ClienteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nombre: String,
    val telefono: String,
    val correo: String? = null,
    @ColumnInfo(name = "fecha_nacimiento")
    val fechaNacimiento: String? = null,
    val direccion: String,
    val observaciones: String? = null,
    val estatus: String = "ACTIVO"
)