package com.esplora.domain.interactors

import app.cash.turbine.test
import com.esplora.FakeEsploraApiService
import com.esplora.TestFixtures
import com.esplora.data.repository.DefaultEsploraRepository
import com.esplora.store.local.DefaultEsploraLocalDataSource
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class ObserveTransactionsInteractorTest {

    private lateinit var interactor: ObserveTransactionsInteractor
    private lateinit var repository: DefaultEsploraRepository
    private lateinit var apiService: FakeEsploraApiService
    private lateinit var localStore: DefaultEsploraLocalDataSource
    private val testScope = TestScope()

    @BeforeTest
    fun setup() {
        apiService = FakeEsploraApiService()
        localStore = DefaultEsploraLocalDataSource()
        repository = DefaultEsploraRepository(apiService, localStore)
        interactor = ObserveTransactionsInteractor(repository)
    }

    @Test
    fun `invoke should start polling and update transactions`() = runTest {
        interactor(this, ObserveTransactionsInteractor.Params("testAddress"))

        interactor.observe().test {
            assertNull(awaitItem())

            advanceTimeBy(10_000)

            val transactions = awaitItem()
            assertNotNull(transactions)
            assertEquals(1, transactions.size)
            assertEquals(TestFixtures.dummyTransactionHighValue.transactionId, transactions[0].transactionId)

            interactor.stopPolling()
            cancelAndIgnoreRemainingEvents()
        }
    }
}