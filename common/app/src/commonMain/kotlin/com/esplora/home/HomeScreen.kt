package com.esplora.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(viewModel:HomeViewModel = koinViewModel()) {
    val balanceState by viewModel.balanceState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(onClick = { viewModel.fetchBalance("tb1qtzrhlwxqcsufs8hvg4c3w33utf9hat4x9xlrf7") }) {
            Text("Fetch Balance")
        }

        balanceState?.let { balance ->
            Text("Funded: ${balance.chainStats.fundedTxoSum}")
            Text("Spent: ${balance.chainStats.spentTxoSum}")
        } ?: Text("No balance data yet.")
    }
}

