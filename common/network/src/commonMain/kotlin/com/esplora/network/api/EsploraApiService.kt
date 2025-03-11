package com.esplora.network.api

import com.esplora.models.Utxo
import com.esplora.models.Transaction


interface EsploraApiService {
    suspend fun getBalance(address: String): List<Utxo>
    suspend fun getTransactions(address: String): List<Transaction>
}