package com.esplora

import com.esplora.models.*

object TestFixtures {
    val dummyUtxoConfirmed = Utxo(
        txId = "confirmed-txid-001",
        vOut = 0,
        status = UtxoStatus(
            confirmed = true,
            blockHeight = 700000,
            blockHash = "confirmed-block-hash",
            blockTime = 1678901234L
        ),
        value = 0.05
    )

    val dummyUtxoUnconfirmed = Utxo(
        txId = "unconfirmed-txid-002",
        vOut = 1,
        status = UtxoStatus(
            confirmed = false,
            blockHeight = null,
            blockHash = null,
            blockTime = null
        ),
        value = 0.01
    )

    val dummyTransactionHighValue = Transaction(
        transactionId = "highvalue-txid-003",
        fee = 0.0005,
        status = TransactionStatus(
            confirmed = true,
            blockHeight = 699999
        )
    )

    val dummyTransactionUnconfirmed = Transaction(
        transactionId = "unconfirmed-txid-004",
        fee = 0.0002,
        status = TransactionStatus(
            confirmed = false,
            blockHeight = null
        )
    )

    val dummyBalanceZero = BalanceByAddress(
        address = "bc1qemptyaddress",
        balance = 0.0,
        utxos = emptyList()
    )

    val dummyBalanceMultipleUtxos = BalanceByAddress(
        address = "bc1qmultiutxo",
        balance = 0.06,
        utxos = listOf(dummyUtxoConfirmed, dummyUtxoUnconfirmed)
    )
}