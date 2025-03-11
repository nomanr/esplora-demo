package com.esplora.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Balance(
    @SerialName("chain_stats") val chainStats: ChainStats,
    @SerialName("mempool_stats") val mempoolStats: MempoolStats
)

@Serializable
data class ChainStats(
    @SerialName("funded_txo_sum") val fundedTxoSum: Long,
    @SerialName("spent_txo_sum") val spentTxoSum: Long
)

@Serializable
data class MempoolStats(
    @SerialName("funded_txo_sum") val fundedTxoSum: Long,
    @SerialName("spent_txo_sum") val spentTxoSum: Long
)

@Serializable
data class Transaction(
    @SerialName("txid") val transactionId: String,
    @SerialName("fee") val fee: Int,
    @SerialName("status") val status: TransactionStatus
)

@Serializable
data class TransactionStatus(
    @SerialName("confirmed") val confirmed: Boolean,
    @SerialName("block_height") val blockHeight: Int?
)
