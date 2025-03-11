package com.esplora.data.repository

import com.esplora.network.api.EsploraApiService
import com.esplora.network.models.*

class DefaultEsploraRepository(private val apiService: EsploraApiService) : EsploraRepository {

    override suspend fun getBalance(address: String): NetworkBalanceResponse =
        apiService.getBalance(address)

    override suspend fun getTransactions(address: String): List<NetworkTransactionResponse> =
        apiService.getTransactions(address)

    override suspend fun getUnconfirmedTransactions(address: String): List<NetworkUnconfirmedTransactionResponse> =
        apiService.getUnconfirmedTransactions(address)

    override suspend fun getTransactionStatus(txid: String): NetworkTransactionStatusResponse =
        apiService.getTransactionStatus(txid)

    override suspend fun getLatestBlockHeight(): Int =
        apiService.getLatestBlockHeight()

    override suspend fun getLatestBlockHash(): String =
        apiService.getLatestBlockHash()

    override suspend fun getMempoolData(): NetworkMempoolResponse =
        apiService.getMempoolData()

    override suspend fun getMempoolTxIds(): List<String> =
        apiService.getMempoolTxIds()

    override suspend fun getRecentMempoolTransactions(): List<NetworkTransactionResponse> =
        apiService.getRecentMempoolTransactions()

    override suspend fun getUTXOs(address: String): List<NetworkUTXOResponse> =
        apiService.getUTXOs(address)
}