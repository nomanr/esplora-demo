package com.esplora.network.api

import com.esplora.models.Utxo
import com.esplora.models.Transaction
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class DefaultEsploraApiService(private val client: HttpClient) : EsploraApiService {

    override suspend fun getBalance(address: String): List<Utxo> =
        client.get("testnet/api/address/$address/utxo").body()

    override suspend fun getTransactions(address: String): List<Transaction> =
        client.get("testnet/api/address/$address/txs").body()

}