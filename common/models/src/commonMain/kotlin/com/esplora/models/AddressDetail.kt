package com.esplora.models

data class BalanceByAddress(
    val address: String,
    val balance: Double,
    val utxos: List<Utxo>
)