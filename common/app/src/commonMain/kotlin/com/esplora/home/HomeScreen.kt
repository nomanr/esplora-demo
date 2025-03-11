package com.esplora.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.esplora.models.BalanceByAddress
import com.esplora.uicomponents.AppTheme
import com.esplora.uicomponents.components.Scaffold
import com.esplora.uicomponents.components.Text
import com.esplora.uicomponents.components.card.OutlinedCard
import com.esplora.utils.Formatter
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = koinViewModel()) {
    val addresses by viewModel.addresses.collectAsStateWithLifecycle()
    val totalBalance by derivedStateOf {
        addresses?.sumOf { address -> address.balance } ?: 0.0
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(horizontal = 16.dp).padding(paddingValues)
        ) {
            BalanceCard(totalBalance = totalBalance)
            Spacer(modifier = Modifier.padding(8.dp))
            AddressList(addresses = addresses ?: emptyList())
        }
    }
}

@Composable
fun BalanceCard(totalBalance: Double) {
    Box(
        modifier = Modifier.fillMaxWidth().padding(8.dp), contentAlignment = Alignment.Center
    ) {

        Text(text = Formatter.formatBalance(totalBalance), style = AppTheme.typography.h1)
    }
}

@Composable
fun AddressList(addresses: List<BalanceByAddress>) {
    LazyColumn {
        items(addresses.size) { index ->
            AddressListItem(address = addresses[index])
        }
    }
}

@Composable
fun AddressListItem(address: BalanceByAddress) {
    OutlinedCard(
        modifier = Modifier.padding(top = 8.dp)
    ) {
        Column(
            Modifier.fillMaxWidth().padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = Formatter.formatAddress(address.address), style = AppTheme.typography.h4)
            Text(text = Formatter.formatBalance(address.balance), style = AppTheme.typography.label1)
        }
    }
}
