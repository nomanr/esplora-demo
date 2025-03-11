package com.esplora.network.api

import com.esplora.network.models.NetworkBalanceResponse
import com.esplora.network.models.NetworkMempoolResponse
import com.esplora.network.models.NetworkTransactionResponse
import com.esplora.network.models.NetworkTransactionStatusResponse
import com.esplora.network.models.NetworkUTXOResponse
import com.esplora.network.models.NetworkUnconfirmedTransactionResponse

interface EsploraApiService {
    suspend fun getBalance(address: String): NetworkBalanceResponse

    suspend fun getTransactions(address: String): List<NetworkTransactionResponse>

    suspend fun getUnconfirmedTransactions(address: String): List<NetworkUnconfirmedTransactionResponse>

    suspend fun getTransactionStatus(txid: String): NetworkTransactionStatusResponse

    suspend fun getLatestBlockHeight(): Int

    suspend fun getLatestBlockHash(): String

    suspend fun getMempoolData(): NetworkMempoolResponse

    suspend fun getMempoolTxIds(): List<String>

    suspend fun getRecentMempoolTransactions(): List<NetworkTransactionResponse>

    suspend fun getUTXOs(address: String): List<NetworkUTXOResponse>
}
