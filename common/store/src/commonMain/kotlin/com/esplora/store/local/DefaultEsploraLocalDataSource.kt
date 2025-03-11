package com.esplora.store.local

import com.esplora.models.BalanceByAddress
import com.esplora.models.Transaction
import com.esplora.models.Utxo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

//TODO: for now its all in-memory, but in real world we should use some database to store this data.
class DefaultEsploraLocalDataSource : EsploraLocalDataSource {
    private val mutex = Mutex()
    private val utxoCache = MutableStateFlow<Map<String, BalanceByAddress>>(emptyMap())
    private val transactionsCache = MutableStateFlow<Map<String, List<Transaction>>>(emptyMap())

    override fun observeBalance(address: String): Flow<BalanceByAddress?> = utxoCache.map { it[address] }.distinctUntilChanged()

    override fun observeAllBalances(): Flow<List<BalanceByAddress>> = utxoCache.map { it.values.toList() }.distinctUntilChanged()

    override fun observeTransactions(address: String): Flow<List<Transaction>?> =
        transactionsCache.map { it[address] }.distinctUntilChanged()

    override fun observeAllTransactions(): Flow<Map<String, List<Transaction>>> = transactionsCache

    override suspend fun updateBalance(address: String, utxo: List<Utxo>) {
        mutex.withLock {
            val totalBalance = utxo.sumOf { it.value }
            utxoCache.value = utxoCache.value.toMutableMap().apply {
                put(address, BalanceByAddress(address, totalBalance, utxo))
            }
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
            utxoCache.value = emptyMap()
            transactionsCache.value = emptyMap()
        }
    }
}