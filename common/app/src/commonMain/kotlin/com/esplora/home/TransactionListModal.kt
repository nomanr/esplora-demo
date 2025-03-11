package com.esplora.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.esplora.models.BalanceByAddress
import com.esplora.models.Transaction
import com.esplora.uicomponents.AppTheme
import com.esplora.uicomponents.components.ModalBottomSheet
import com.esplora.uicomponents.components.Text
import com.esplora.uicomponents.components.card.OutlinedCard
import com.esplora.utils.Formatter
import com.nomanr.composables.bottomsheet.rememberModalBottomSheetState

@Composable
fun TransactionListModal(
    selectedAddress: BalanceByAddress?, transactionList: List<Transaction>, onHideModal: () -> Unit
) {
    if (selectedAddress != null) {
        ModalBottomSheet(modifier = Modifier.padding(top = 100.dp),
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            onDismissRequest = { onHideModal() }) {
            LazyColumn {
                item {
                    Text(
                        text = "Transactions", style = AppTheme.typography.h3, modifier = Modifier.padding(16.dp)
                    )
                }

                transactionList.forEach {
                    item {
                        TransactionListItem(transaction = it)
                    }
                }
            }
        }
    }
}

//FIXME: Add more details e.g. date
@Composable
fun TransactionListItem(transaction: Transaction) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = transaction.transactionId, style = AppTheme.typography.h4
                )
                Text(
                    text = if (transaction.status.confirmed) "Confirmed" else "Pending",
                    style = AppTheme.typography.body2,
                    color = if (transaction.status.confirmed) AppTheme.colors.success else AppTheme.colors.error
                )
            }

            Spacer(modifier = Modifier.weight(0.1f))
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Fee: ${Formatter.formatBalance(transaction.fee)}",
                    style = AppTheme.typography.body2,
                    color = AppTheme.colors.onSurface.copy(alpha = 0.7f)
                )
                transaction.status.blockHeight?.let {
                    Text(
                        text = "Block: $it", style = AppTheme.typography.body2, color = AppTheme.colors.onSurface.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}
