package com.danidev.composelearn.app

import kotlinx.serialization.Serializable

sealed interface ComposeLearnDestination

@Serializable
data object HomeDestination : ComposeLearnDestination

@Serializable
data object PuestosDestination : ComposeLearnDestination

@Serializable
data object ClientesDestination : ComposeLearnDestination

@Serializable
data class ClienteDetailDestination(val clienteId: Long) : ComposeLearnDestination
