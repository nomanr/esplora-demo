package com.esplora.data.repository

import com.esplora.models.Balance
import com.esplora.models.Transaction
import kotlinx.coroutines.flow.Flow


interface EsploraRepository {
    suspend fun getBalance(address: String): Balance
    suspend fun getTransactions(address: String): List<Transaction>
    suspend fun fetchAndStoreBalance(address: String)
    suspend fun fetchAndStoreTransactions(address: String)
    fun observeAllBalances(): Flow<Map<String, Balance>>
    fun observeAllTransactions(): Flow<Map<String, List<Transaction>>>
}