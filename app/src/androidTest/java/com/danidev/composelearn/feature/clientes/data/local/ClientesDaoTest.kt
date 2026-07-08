package com.danidev.composelearn.feature.clientes.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.danidev.composelearn.core.database.AppDatabase
import com.danidev.composelearn.feature.puestos.data.local.PuestoEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ClientesDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var clientesDao: ClientesDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).allowMainThreadQueries()
            .build()

        clientesDao = database.clientesDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertCliente_guardaRegistro() = runTest {
        clientesDao.insertCliente(
            ClienteEntity(
                nombre = "Cliente Centro",
                telefono = "5512345678",
                direccion = "Av Centro 123"
            )
        )

        val clientes = clientesDao.observeClientes().first()

        assertEquals(1, clientes.size)
        assertEquals("Cliente Centro", clientes[0].nombre)
        assertEquals("5512345678", clientes[0].telefono)
    }

    @Test
    fun observeClientes_ordenaPorNombreAscendente() = runTest {
        clientesDao.insertCliente(
            ClienteEntity(
                nombre = "Zapateria Norte",
                telefono = "5511111111",
                direccion = "Calle Norte 10"
            )
        )

        clientesDao.insertCliente(
            ClienteEntity(
                nombre = "Abarrotes Centro",
                telefono = "5522222222",
                direccion = "Calle Centro 20"
            )
        )

        val clientes = clientesDao.observeClientes().first()

        assertEquals("Abarrotes Centro", clientes[0].nombre)
        assertEquals("Zapateria Norte", clientes[1].nombre)
    }

    @Test
    fun updateCliente_actualizaRegistro() = runTest {
        clientesDao.insertCliente(
            ClienteEntity(
                id = 1L,
                nombre = "Cliente Centro",
                telefono = "5512345678",
                direccion = "Av Centro 123"
            )
        )

        clientesDao.updateCliente(
            ClienteEntity(
                id = 1L,
                nombre = "Cliente Centro Actualizado",
                telefono = "5599999999",
                correo = "cliente@correo.com",
                direccion = "Av Nueva 456",
                observaciones = "Cliente frecuente"
            )
        )

        val cliente = clientesDao.observeClienteById(1L).first()

        assertEquals("Cliente Centro Actualizado", cliente?.nombre)
        assertEquals("5599999999", cliente?.telefono)
        assertEquals("cliente@correo.com", cliente?.correo)
        assertEquals("Av Nueva 456", cliente?.direccion)
        assertEquals("Cliente frecuente", cliente?.observaciones)
    }

    @Test
    fun deactivateCliente_cambiaEstatusAInactivo() = runTest {
        clientesDao.insertCliente(
            ClienteEntity(
                id = 1L,
                nombre = "Cliente Centro",
                telefono = "5512345678",
                direccion = "Av Centro 123"
            )
        )

        clientesDao.deactivateCliente(1L)

        val cliente = clientesDao.observeClienteById(1L).first()

        assertEquals("INACTIVO", cliente?.estatus)
    }

    @Test
    fun assignPuestoToCliente_guardaRelacion() = runTest {
        val clienteId = clientesDao.insertCliente(
            ClienteEntity(
                nombre = "Cliente Centro",
                telefono = "5512345678",
                direccion = "Av Centro 123"
            )
        )

        val puestoId = 1L

        database.puestosDao().insertPuesto(
            PuestoEntity(
                id = puestoId,
                nombre = "Gerente",
                descripcion = "Administra personal"
            )
        )

        clientesDao.assignPuestoToCliente(
            ClientePuestoCrossRef(
                clienteId = clienteId,
                puestoId = puestoId
            )
        )

        val puestosIds = clientesDao.observePuestoIdsByCliente(clienteId).first()

        assertEquals(listOf(puestoId), puestosIds)
    }

    @Test
    fun removePuestoFromCliente_eliminaRelacion() = runTest {
        val clienteId = clientesDao.insertCliente(
            ClienteEntity(
                nombre = "Cliente Centro",
                telefono = "5512345678",
                direccion = "Av Centro 123"
            )
        )

        val puestoId = 1L

        database.puestosDao().insertPuesto(
            PuestoEntity(
                id = puestoId,
                nombre = "Gerente",
                descripcion = "Administra personal"
            )
        )

        clientesDao.assignPuestoToCliente(
            ClientePuestoCrossRef(
                clienteId = clienteId,
                puestoId = puestoId
            )
        )

        clientesDao.removePuestoFromCliente(
            clienteId = clienteId,
            puestoId = puestoId
        )

        val puestosIds = clientesDao.observePuestoIdsByCliente(clienteId).first()

        assertEquals(emptyList<Long>(), puestosIds)
    }
}