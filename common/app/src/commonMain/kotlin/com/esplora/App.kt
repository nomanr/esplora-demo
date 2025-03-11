package com.esplora

import androidx.compose.runtime.Composable
import com.esplora.home.HomeScreen
import com.esplora.uicomponents.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext


@Composable
@Preview
fun App() {
   AppTheme {
       KoinContext {
            HomeScreen()
       }
   }
}