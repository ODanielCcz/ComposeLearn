package com.danidev.composelearn.feature.clientes.di

import com.danidev.composelearn.feature.clientes.domain.repository.ClientesRepository
import com.danidev.composelearn.feature.clientes.data.repository.ClientesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ClientesModule {

    @Binds
    @Singleton
    abstract fun bindClientesRepository(
        implementation: ClientesRepositoryImpl
    ): ClientesRepository
}