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
class ObserveBalanceInteractorTest {

    private lateinit var interactor: ObserveBalanceInteractor
    private lateinit var repository: DefaultEsploraRepository
    private lateinit var apiService: FakeEsploraApiService
    private lateinit var localStore: DefaultEsploraLocalDataSource

    @BeforeTest
    fun setup() {
        apiService = FakeEsploraApiService()
        localStore = DefaultEsploraLocalDataSource()
        repository = DefaultEsploraRepository(apiService, localStore)
        interactor = ObserveBalanceInteractor(repository)
    }

    @Test
    fun `invoke should start polling and update balances`() = runTest {
        val testAddresses = listOf("testAddress")
        interactor(this, ObserveBalanceInteractor.Params(testAddresses))

        interactor.observe().test {
            assertTrue(awaitItem().isEmpty())

            advanceTimeBy(10_000)

            val updatedBalances = awaitItem()
            assertEquals(1, updatedBalances.size)
            assertEquals(TestFixtures.dummyUtxoConfirmed.value, updatedBalances[0].balance)

            interactor.stopPolling()
            cancelAndIgnoreRemainingEvents()
        }
    }
}