package com.esplora.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

//TODO: A UTXO is just an unspent tx, we can use Transaction as parent
//TODO: Better Network and Local models separation and ext functions for conversion

@Serializable
data class Utxo(
    @SerialName("txid") val txId: String,
    @SerialName("vout") val vOut: Int,
    @SerialName("status") val status: UtxoStatus,
    @SerialName("value") val value: Double
)

@Serializable
data class UtxoStatus(
    @SerialName("confirmed") val confirmed: Boolean,
    @SerialName("block_height") val blockHeight: Int? = null,
    @SerialName("block_hash") val blockHash: String? = null,
    @SerialName("block_time") val blockTime: Long? = null
)

@Serializable
data class Transaction(
    @SerialName("txid") val transactionId: String,
    @SerialName("fee") val fee: Double,
    @SerialName("status") val status: TransactionStatus
)

@Serializable
data class TransactionStatus(
    @SerialName("confirmed") val confirmed: Boolean,
    @SerialName("block_height") val blockHeight: Int?
)
