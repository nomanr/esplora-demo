package com.esplora.data.repository.fake

import com.esplora.TestFixtures
import com.esplora.models.Utxo
import com.esplora.models.Transaction
import com.esplora.network.api.EsploraApiService

class FakeEsploraApiService : EsploraApiService {
    override suspend fun getBalance(address: String): List<Utxo> {
        return when (address) {
            "testAddress" -> listOf(TestFixtures.dummyUtxoConfirmed)
            "emptyAddress" -> emptyList()
            else -> throw IllegalArgumentException("Unknown address")
        }
    }

    override suspend fun getTransactions(address: String): List<Transaction> {
        return when (address) {
            "testAddress" -> listOf(TestFixtures.dummyTransactionHighValue)
            "noTxAddress" -> emptyList()
            else -> throw IllegalArgumentException("Unknown address")
        }
    }
}