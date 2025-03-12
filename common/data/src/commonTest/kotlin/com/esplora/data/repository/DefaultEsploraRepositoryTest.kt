package com.esplora.data.repository

import app.cash.turbine.test
import com.esplora.FakeEsploraApiService
import com.esplora.TestFixtures
import com.esplora.store.local.DefaultEsploraLocalDataSource
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class DefaultEsploraRepositoryTest {

    private lateinit var repository: DefaultEsploraRepository
    private lateinit var apiService: FakeEsploraApiService
    private lateinit var localStore: DefaultEsploraLocalDataSource

    @BeforeTest
    fun setup() {
        apiService = FakeEsploraApiService()
        localStore = DefaultEsploraLocalDataSource()
        repository = DefaultEsploraRepository(apiService, localStore)
    }

    @Test
    fun `getBalance should return expected UTXOs`() = runTest {
        val balance = repository.getBalance("testAddress")
        assertEquals(1, balance.size)
        assertEquals(TestFixtures.dummyUtxoConfirmed.txId, balance[0].txId)
    }

    @Test
    fun `getTransactions should return expected transactions`() = runTest {
        val transactions = repository.getTransactions("testAddress")
        assertEquals(1, transactions.size)
        assertEquals(TestFixtures.dummyTransactionHighValue.transactionId, transactions[0].transactionId)
    }

    @Test
    fun `fetchAndStoreBalance should update localStore`() = runTest {
        repository.fetchAndStoreBalance("testAddress")

        localStore.observeBalance("testAddress").test {
            val balance = awaitItem()
            assertNotNull(balance)
            assertEquals("testAddress", balance.address)
            assertEquals(TestFixtures.dummyUtxoConfirmed.value, balance.balance)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchAndStoreTransactions should update localStore`() = runTest {
        repository.fetchAndStoreTransactions("testAddress")

        localStore.observeTransactions("testAddress").test {
            val transactions = awaitItem()
            assertNotNull(transactions)
            assertEquals(1, transactions.size)
            assertEquals(TestFixtures.dummyTransactionHighValue.transactionId, transactions[0].transactionId)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `observeAllBalances should emit updated balances`() = runTest {
        repository.fetchAndStoreBalance("testAddress")

        repository.observeAllBalances().test {
            val balances = awaitItem()
            assertEquals(1, balances.size)
            assertEquals("testAddress", balances[0].address)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `observeAllTransactions should emit updated transactions`() = runTest {
        repository.fetchAndStoreTransactions("testAddress")

        repository.observeAllTransactions().test {
            val transactions = awaitItem()
            assertEquals(1, transactions.size)
            assertEquals("testAddress", transactions.keys.first())

            cancelAndIgnoreRemainingEvents()
        }
    }
}