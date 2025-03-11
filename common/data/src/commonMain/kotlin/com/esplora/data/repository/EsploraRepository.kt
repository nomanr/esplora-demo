package com.esplora.data.repository

import com.esplora.models.BalanceByAddress
import com.esplora.models.Utxo
import com.esplora.models.Transaction
import kotlinx.coroutines.flow.Flow


interface EsploraRepository {
    suspend fun getBalance(address: String): List<Utxo>
    suspend fun getTransactions(address: String): List<Transaction>
    suspend fun fetchAndStoreBalance(address: String)
    suspend fun fetchAndStoreTransactions(address: String)
    fun observeAllBalances(): Flow<List<BalanceByAddress>>
    fun observeAllTransactions(): Flow<Map<String, List<Transaction>>>
    fun observeTransactionsByAddress(address: String): Flow<List<Transaction>?>
}