package com.esplora.store.local

import com.esplora.models.Balance
import com.esplora.models.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

//TODO: for now its all in-memory, but in real world we should use some database to store this data.
class DefaultEsploraLocalDataSource : EsploraLocalDataSource {
    private val mutex = Mutex()
    private val balanceCache = MutableStateFlow<Map<String, Balance>>(emptyMap())
    private val transactionsCache = MutableStateFlow<Map<String, List<Transaction>>>(emptyMap())

    override fun observeBalance(address: String): Flow<Balance?> =
        balanceCache.map { it[address] }.distinctUntilChanged()

    override fun observeAllBalances(): Flow<Map<String, Balance>> = balanceCache

    override fun observeTransactions(address: String): Flow<List<Transaction>?> =
        transactionsCache.map { it[address] }.distinctUntilChanged()

    override fun observeAllTransactions(): Flow<Map<String, List<Transaction>>> = transactionsCache

    override suspend fun updateBalance(address: String, balance: Balance) {
        mutex.withLock {
            balanceCache.value = balanceCache.value.toMutableMap().apply { put(address, balance) }
        }
    }

    override suspend fun updateTransactions(address: String, transactions: List<Transaction>) {
        mutex.withLock {
            val existingTransactions = transactionsCache.value[address] ?: emptyList()

            val newUniqueTransactions = transactions.filter { newTx ->
                existingTransactions.none { it.transactionId == newTx.transactionId }
            }

            if (newUniqueTransactions.isNotEmpty()) {
                transactionsCache.value = transactionsCache.value.toMutableMap().apply {
                    put(address, existingTransactions + newUniqueTransactions)
                }
            }
        }
    }

    override suspend fun clear() {
        mutex.withLock {
            balanceCache.value = emptyMap()
            transactionsCache.value = emptyMap()
        }
    }
}