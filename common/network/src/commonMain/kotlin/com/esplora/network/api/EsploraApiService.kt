package com.esplora.network.api

import com.esplora.models.Balance
import com.esplora.models.Transaction


interface EsploraApiService {
    suspend fun getBalance(address: String): Balance
    suspend fun getTransactions(address: String): List<Transaction>
}