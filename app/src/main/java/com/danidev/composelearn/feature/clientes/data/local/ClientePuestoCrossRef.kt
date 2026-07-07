package com.danidev.composelearn.feature.clientes.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.danidev.composelearn.feature.puestos.data.local.PuestoEntity

@Entity(
    tableName = "cliente_puesto",
    primaryKeys = ["cliente_id", "puesto_id"],
    foreignKeys = [
        ForeignKey(
            entity = ClienteEntity::class,
            parentColumns = ["id"],
            childColumns = ["cliente_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PuestoEntity::class,
            parentColumns = ["id"],
            childColumns = ["puesto_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["cliente_id"]),
        Index(value = ["puesto_id"])
    ]
)
data class ClientePuestoCrossRef(
    @ColumnInfo(name = "cliente_id")
    val clienteId: Long,
    @ColumnInfo(name = "puesto_id")
    val puestoId: Long
)