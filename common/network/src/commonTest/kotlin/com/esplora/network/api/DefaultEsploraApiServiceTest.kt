package com.esplora.network.api

import com.esplora.TestFixtures
import com.esplora.models.Transaction
import com.esplora.models.Utxo
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class FakeEsploraApiServiceTest {

    private lateinit var apiService: FakeEsploraApiService

    @BeforeTest
    fun setup() {
        apiService = FakeEsploraApiService()
    }

    @Test
    fun `should return a confirmed UTXO for testAddress`() = runTest {
        val result: List<Utxo> = apiService.getBalance("testAddress")

        assertEquals(1, result.size)
        assertEquals(TestFixtures.dummyUtxoConfirmed.txId, result[0].txId)
        assertTrue(result[0].status.confirmed)
    }

    @Test
    fun `should return empty list for emptyAddress`() = runTest {
        val result: List<Utxo> = apiService.getBalance("emptyAddress")

        assertTrue(result.isEmpty())
    }

    @Test
    fun `should throw exception for unknown address in getBalance`() = runTest {
        val exception = assertFailsWith<IllegalArgumentException> {
            apiService.getBalance("unknownAddress")
        }
        assertEquals("Unknown address", exception.message)
    }

    @Test
    fun `should return high value transaction for testAddress`() = runTest {
        val result: List<Transaction> = apiService.getTransactions("testAddress")

        assertEquals(1, result.size)
        assertEquals(TestFixtures.dummyTransactionHighValue.transactionId, result[0].transactionId)
        assertTrue(result[0].status.confirmed)
    }

    @Test
    fun `should return empty transaction list for noTxAddress`() = runTest {
        val result: List<Transaction> = apiService.getTransactions("noTxAddress")

        assertTrue(result.isEmpty())
    }

    @Test
    fun `should throw exception for unknown address in getTransactions`() = runTest {
        val exception = assertFailsWith<IllegalArgumentException> {
            apiService.getTransactions("unknownAddress")
        }
        assertEquals("Unknown address", exception.message)
    }
}