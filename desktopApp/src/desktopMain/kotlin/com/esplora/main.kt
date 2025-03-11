package com.esplora

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(appModule)
    }
    val windowState = WindowState(
        size = DpSize(400.dp, 800.dp),
    )

    return singleWindowApplication(windowState) {
        App()
    }
}