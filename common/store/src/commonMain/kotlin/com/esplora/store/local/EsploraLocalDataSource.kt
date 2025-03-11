package com.esplora.store.local

import com.esplora.models.Balance
import com.esplora.models.Transaction
import kotlinx.coroutines.flow.Flow

interface EsploraLocalDataSource {
    fun observeBalance(address: String): Flow<Balance?>
    fun observeAllBalances(): Flow<Map<String, Balance>>
    fun observeTransactions(address: String): Flow<List<Transaction>?>
    fun observeAllTransactions(): Flow<Map<String, List<Transaction>>>

    suspend fun updateBalance(address: String, balance: Balance)
    suspend fun updateTransactions(address: String, transactions: List<Transaction>)

    suspend fun clear()
}