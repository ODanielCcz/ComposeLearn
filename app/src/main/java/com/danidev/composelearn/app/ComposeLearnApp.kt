package com.danidev.composelearn.app

import androidx.compose.runtime.Composable
import com.danidev.composelearn.ui.theme.ComposeLearnTheme

@Composable
fun ComposableLearnApp() {
    ComposeLearnTheme {
        ComposeLearnNavDisplay()
    }
}