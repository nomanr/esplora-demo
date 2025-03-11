package com.esplora.network.api

import com.esplora.models.Balance
import com.esplora.models.Transaction
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class DefaultEsploraApiService(private val client: HttpClient) : EsploraApiService {

    override suspend fun getBalance(address: String): Balance =
        client.get("address/$address").body()

    override suspend fun getTransactions(address: String): List<Transaction> =
        client.get("address/$address/txs").body()

}