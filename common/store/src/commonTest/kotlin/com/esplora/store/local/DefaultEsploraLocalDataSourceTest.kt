package com.esplora.store.local

import app.cash.turbine.test
import com.esplora.TestFixtures
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class DefaultEsploraLocalDataSourceTest {

    private lateinit var dataSource: DefaultEsploraLocalDataSource

    @BeforeTest
    fun setup() {
        dataSource = DefaultEsploraLocalDataSource()
    }

    @Test
    fun `observeBalance should emit null initially`() = runTest {
        dataSource.observeBalance("testAddress").test {
            assertNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateBalance should emit new balance`() = runTest {
        val address = "testAddress"
        val utxoList = listOf(TestFixtures.dummyUtxoConfirmed)

        dataSource.observeBalance(address).test {
            assertNull(awaitItem())

            dataSource.updateBalance(address, utxoList)

            val updatedBalance = awaitItem()
            println("Emitted balance: ${address} ${updatedBalance}")

            assertNotNull(updatedBalance)
            assertEquals(address, updatedBalance.address)
            assertEquals(utxoList.sumOf { it.value }, updatedBalance.balance)
            assertEquals(utxoList, updatedBalance.utxos)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `observeAllBalances should emit updated balances`() = runTest {
        dataSource.observeAllBalances().test {
            assertTrue(awaitItem().isEmpty())

            dataSource.updateBalance("addr1", listOf(TestFixtures.dummyUtxoConfirmed))
            dataSource.updateBalance("addr2", listOf(TestFixtures.dummyUtxoUnconfirmed))

            val latestBalances = expectMostRecentItem()
            assertEquals(2, latestBalances.size)
            assertEquals(setOf("addr1", "addr2"), latestBalances.map { it.address }.toSet())

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `observeTransactions should emit null initially`() = runTest {
        dataSource.observeTransactions("unknownAddress").test {
            assertNull(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateTransactions should emit new transactions`() = runTest {
        val address = "testAddress"
        val transactions = listOf(TestFixtures.dummyTransactionHighValue)

        dataSource.observeTransactions(address).test {
            assertNull(awaitItem())
            
            dataSource.updateTransactions(address, transactions)

            val storedTransactions = awaitItem()
            assertNotNull(storedTransactions)
            assertEquals(1, storedTransactions.size)
            assertEquals(transactions[0].transactionId, storedTransactions[0].transactionId)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `updateTransactions should not add duplicate transactions`() = runTest {
        val address = "testAddress"
        val transactions = listOf(TestFixtures.dummyTransactionHighValue)

        dataSource.observeTransactions(address).test {
            assertNull(awaitItem())

            dataSource.updateTransactions(address, transactions)
            dataSource.updateTransactions(address, transactions)

            val storedTransactions = awaitItem()
            assertEquals(1, storedTransactions!!.size)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `clear should remove all stored balances and transactions`() = runTest {
        dataSource.updateBalance("addr1", listOf(TestFixtures.dummyUtxoConfirmed))
        dataSource.updateTransactions("addr1", listOf(TestFixtures.dummyTransactionHighValue))

        dataSource.clear()

        dataSource.observeAllBalances().test {
            assertTrue(awaitItem().isEmpty())
            cancelAndIgnoreRemainingEvents()
        }

        dataSource.observeAllTransactions().test {
            assertTrue(awaitItem().isEmpty())
            cancelAndIgnoreRemainingEvents()
        }
    }
}