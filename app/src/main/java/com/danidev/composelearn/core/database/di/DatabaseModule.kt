package com.danidev.composelearn.core.database.di

import android.content.Context
import androidx.room.Room
import com.danidev.composelearn.core.database.AppDatabase
import com.danidev.composelearn.feature.puestos.data.local.PuestosDao
import com.danidev.composelearn.feature.clientes.data.local.ClientesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "compose_learn.db"
        ).build()
    }

    @Provides
    fun providePuestosDao(
        database: AppDatabase
    ): PuestosDao {
        return database.puestosDao()
    }

    @Provides
    fun provideClientesDao(
        database: AppDatabase
    ): ClientesDao {
        return database.clientesDao()
    }
}