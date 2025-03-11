package com.esplora.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkBalanceResponse(
    @SerialName("chain_stats") val chainStats: NetworkChainStats,
    @SerialName("mempool_stats") val mempoolStats: NetworkMempoolStats
)

@Serializable
data class NetworkChainStats(
    @SerialName("funded_txo_count") val fundedTxoCount: Int,
    @SerialName("funded_txo_sum") val fundedTxoSum: Long,
    @SerialName("spent_txo_count") val spentTxoCount: Int,
    @SerialName("spent_txo_sum") val spentTxoSum: Long,
    @SerialName("tx_count") val txCount: Int
)

@Serializable
data class NetworkMempoolStats(
    @SerialName("funded_txo_count") val fundedTxoCount: Int,
    @SerialName("funded_txo_sum") val fundedTxoSum: Long,
    @SerialName("spent_txo_count") val spentTxoCount: Int,
    @SerialName("spent_txo_sum") val spentTxoSum: Long,
    @SerialName("tx_count") val txCount: Int
)

@Serializable
data class NetworkTransactionResponse(
    @SerialName("txid") val transactionId: String,
    @SerialName("version") val version: Int,
    @SerialName("locktime") val lockTime: Int,
    @SerialName("size") val size: Int,
    @SerialName("weight") val weight: Int,
    @SerialName("fee") val fee: Int,
    @SerialName("status") val status: NetworkTransactionStatus
)

@Serializable
data class NetworkTransactionStatus(
    @SerialName("confirmed") val confirmed: Boolean,
    @SerialName("block_height") val blockHeight: Int?,
    @SerialName("block_hash") val blockHash: String?,
    @SerialName("block_time") val blockTime: Long?
)

@Serializable
data class NetworkUnconfirmedTransactionResponse(
    @SerialName("txid") val transactionId: String,
    @SerialName("fee") val fee: Int,
    @SerialName("weight") val weight: Int
)

@Serializable
data class NetworkTransactionStatusResponse(
    @SerialName("confirmed") val confirmed: Boolean,
    @SerialName("block_height") val blockHeight: Int?,
    @SerialName("block_hash") val blockHash: String?
)

@Serializable
data class NetworkMempoolResponse(
    @SerialName("count") val count: Int,
    @SerialName("vsize") val virtualSize: Int,
    @SerialName("total_fee") val totalFee: Long,
    @SerialName("fee_histogram") val feeHistogram: List<List<Double>>
)

@Serializable
data class NetworkUTXOResponse(
    @SerialName("txid") val transactionId: String,
    @SerialName("vout") val vout: Int,
    @SerialName("value") val value: Long,
    @SerialName("status") val status: NetworkTransactionStatus
)