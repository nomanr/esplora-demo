package com.esplora.network.api

import com.esplora.network.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class DefaultEsploraApiService(private val client: HttpClient) : EsploraApiService {

    override suspend fun getBalance(address: String): NetworkBalanceResponse =
        client.get("address/$address").body()

    override suspend fun getTransactions(address: String): List<NetworkTransactionResponse> =
        client.get("address/$address/txs").body()

    override suspend fun getUnconfirmedTransactions(address: String): List<NetworkUnconfirmedTransactionResponse> =
        client.get("address/$address/txs/mempool").body()

    override suspend fun getTransactionStatus(txid: String): NetworkTransactionStatusResponse =
        client.get("tx/$txid/status").body()

    override suspend fun getLatestBlockHeight(): Int =
        client.get("blocks/tip/height").body()

    override suspend fun getLatestBlockHash(): String =
        client.get("blocks/tip/hash").body()

    override suspend fun getMempoolData(): NetworkMempoolResponse =
        client.get("mempool").body()

    override suspend fun getMempoolTxIds(): List<String> =
        client.get("mempool/txids").body()

    override suspend fun getRecentMempoolTransactions(): List<NetworkTransactionResponse> =
        client.get("mempool/recent").body()

    override suspend fun getUTXOs(address: String): List<NetworkUTXOResponse> =
        client.get("address/$address/utxo").body()
}