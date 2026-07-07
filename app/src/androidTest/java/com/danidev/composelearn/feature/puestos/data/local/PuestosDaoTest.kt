package com.danidev.composelearn.feature.puestos.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.danidev.composelearn.core.database.AppDatabase
import org.junit.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class PuestosDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var dao: PuestosDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        dao = database.puestosDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertPuesto_guardaRegistro() = runTest {
        dao.insertPuesto(
            PuestoEntity(
                nombre = "Gerente",
                descripcion = "Administra personal"
            )
        )

        val puestos = dao.observePuestos().first()

        assertEquals(1, puestos.size)
        assertEquals("Gerente", puestos[0].nombre)
    }

    @Test
    fun observePuestos_ordenaPorNombreAscendente() = runTest {
        dao.insertPuesto(
            PuestoEntity(
                nombre = "Supervisor",
                descripcion = "Revisa operaciones"
            )
        )
        dao.insertPuesto(
            PuestoEntity(
                nombre = "Cajero",
                descripcion = "Atiende caja"
            )
        )

        val puestos = dao.observePuestos().first()

        assertEquals("Cajero", puestos[0].nombre)
        assertEquals("Supervisor", puestos[1].nombre)
    }

    @Test
    fun updatePuesto_actualizaRegistro() = runTest {
        dao.insertPuesto(
            PuestoEntity(
                id = 1L,
                nombre = "Cajero",
                descripcion = "Atiende caja"
            )
        )

        dao.updatePuesto(
            PuestoEntity(
                id = 1L,
                nombre = "Cajero senior",
                descripcion = "Atiende caja principal"
            )
        )

        val puestos = dao.observePuestos().first()

        assertEquals(1, puestos.size)
        assertEquals("Cajero senior", puestos[0].nombre)
        assertEquals("Atiende caja principal", puestos[0].descripcion)
    }

    @Test
    fun deletePuesto_eliminaRegistro() = runTest {
        dao.insertPuesto(
            PuestoEntity(
                id = 1L,
                nombre = "Gerente",
                descripcion = "Administra personal"
            )
        )

        dao.deletePuesto(1L)

        val puestos = dao.observePuestos().first()

        assertEquals(0, puestos.size)
    }

}