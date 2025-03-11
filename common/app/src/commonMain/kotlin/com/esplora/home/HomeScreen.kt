package com.esplora.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(viewModel:HomeViewModel = koinViewModel()) {
    val balanceState by viewModel.balances.collectAsStateWithLifecycle()

    Column {
        Button(onClick = { viewModel.refresh() }) {
            Text("Refresh Balances")
        }

        balanceState?.forEach { (address, balance) ->
            Text("Address: $address - Balance: ${balance.chainStats.fundedTxoSum}")
        } ?: Text("Loading...")
    }
}

