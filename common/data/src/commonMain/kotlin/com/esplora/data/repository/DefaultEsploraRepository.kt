package com.esplora.data.repository

import com.esplora.models.BalanceByAddress
import com.esplora.models.Utxo
import com.esplora.models.Transaction
import com.esplora.network.api.EsploraApiService
import com.esplora.store.local.EsploraLocalDataSource
import kotlinx.coroutines.flow.Flow

class DefaultEsploraRepository(private val apiService: EsploraApiService, private val localStore: EsploraLocalDataSource) :
    EsploraRepository {

    override suspend fun getBalance(address: String): List<Utxo> = apiService.getBalance(address)

    override suspend fun getTransactions(address: String): List<Transaction> = apiService.getTransactions(address)

    //TODO: Better error handling, for now we consider it'll be success always, but that's not true in real world.
    override suspend fun fetchAndStoreBalance(address: String) {
        try {
            val balance = getBalance(address)
            localStore.updateBalance(address, balance)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //TODO: Same as above, better error handling.
    override suspend fun fetchAndStoreTransactions(address: String) {
        try {
            val transactions = getTransactions(address)
            localStore.updateTransactions(address, transactions)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeAllBalances(): Flow<List<BalanceByAddress>> {
        return localStore.observeAllBalances()
    }

    override fun observeAllTransactions(): Flow<Map<String, List<Transaction>>> {
        return localStore.observeAllTransactions()
    }
}