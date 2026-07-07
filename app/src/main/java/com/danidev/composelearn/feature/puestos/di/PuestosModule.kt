package com.danidev.composelearn.feature.puestos.di

import com.danidev.composelearn.feature.puestos.data.repository.PuestosRepositoryImpl
import com.danidev.composelearn.feature.puestos.domain.repository.PuestosRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PuestosModule {

    @Binds
    @Singleton
    abstract fun bindPuestosRepository(
        implementation: PuestosRepositoryImpl
    ): PuestosRepository
}