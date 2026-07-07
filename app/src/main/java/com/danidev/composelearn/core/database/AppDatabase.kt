package com.danidev.composelearn.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.danidev.composelearn.feature.puestos.data.local.PuestoEntity
import com.danidev.composelearn.feature.puestos.data.local.PuestosDao
import com.danidev.composelearn.feature.clientes.data.local.ClienteEntity
import com.danidev.composelearn.feature.clientes.data.local.ClientePuestoCrossRef
import com.danidev.composelearn.feature.clientes.data.local.ClientesDao

@Database(
    entities = [
        PuestoEntity::class,
        ClienteEntity::class,
        ClientePuestoCrossRef::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun puestosDao(): PuestosDao
    abstract fun clientesDao(): ClientesDao
}