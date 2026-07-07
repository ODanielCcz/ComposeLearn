package com.danidev.composelearn.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.danidev.composelearn.feature.clientes.presentation.ClienteDetailRoute
import com.danidev.composelearn.feature.clientes.presentation.ClientesRoute
import com.danidev.composelearn.feature.home.presentation.HomeRoute
import com.danidev.composelearn.feature.puestos.presentation.PuestosRoute

@Composable
fun ComposeLearnNavDisplay() {
    val backStack = remember {
        mutableStateListOf<ComposeLearnDestination>(HomeDestination)
    }

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeLastOrNull()
            }
        },
        entryProvider = { key ->
            when (key) {
                HomeDestination -> NavEntry(key) {
                    HomeRoute(
                        onPuestosClick = {
                            backStack.add(PuestosDestination)
                        },
                        onPersonalClick = {
                            backStack.add(ClientesDestination)
                        }
                    )
                }

                PuestosDestination -> NavEntry(key) {
                    PuestosRoute()
                }

                ClientesDestination -> NavEntry(key) {
                    ClientesRoute(
                        onClienteClick = { clienteId ->
                            backStack.add(
                                ClienteDetailDestination(clienteId = clienteId)
                            )
                        }
                    )
                }

                is ClienteDetailDestination -> NavEntry(key) {
                    ClienteDetailRoute(
                        clienteId = key.clienteId,
                        onBackClick = {
                            backStack.removeLastOrNull()
                        }
                    )
                }
            }
        }
    )
}