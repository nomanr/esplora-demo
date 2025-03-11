package com.esplora.store.local

import com.esplora.models.BalanceByAddress
import com.esplora.models.Utxo
import com.esplora.models.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface EsploraLocalDataSource {
    fun observeBalance(address: String): Flow<BalanceByAddress?>
    fun observeAllBalances(): Flow<List<BalanceByAddress>>
    fun observeTransactions(address: String): Flow<List<Transaction>?>
    fun observeAllTransactions(): Flow<Map<String, List<Transaction>>>

    suspend fun updateBalance(address: String, utxo: List<Utxo>)
    suspend fun updateTransactions(address: String, transactions: List<Transaction>)

    suspend fun clear()
}