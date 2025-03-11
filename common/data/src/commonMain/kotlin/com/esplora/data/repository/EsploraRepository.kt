package com.esplora.data.repository

import com.esplora.network.models.*

interface EsploraRepository {

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