package com.esplora.home

import app.cash.turbine.test
import com.esplora.FakeEsploraApiService
import com.esplora.TestFixtures
import com.esplora.data.repository.DefaultEsploraRepository
import com.esplora.domain.interactors.GetAddresses
import com.esplora.domain.interactors.ObserveBalanceInteractor
import com.esplora.domain.interactors.ObserveTransactionsInteractor
import com.esplora.models.BalanceByAddress
import com.esplora.store.local.DefaultEsploraLocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel
    private lateinit var balanceInteractor: ObserveBalanceInteractor
    private lateinit var transactionsInteractor: ObserveTransactionsInteractor
    private lateinit var repository: DefaultEsploraRepository
    private lateinit var apiService: FakeEsploraApiService
    private lateinit var localStore: DefaultEsploraLocalDataSource
    private lateinit var getAddresses: GetAddresses

    @BeforeTest
    fun setup() {
        apiService = FakeEsploraApiService()
        localStore = DefaultEsploraLocalDataSource()
        repository = DefaultEsploraRepository(apiService, localStore)
        balanceInteractor = ObserveBalanceInteractor(repository)
        transactionsInteractor = ObserveTransactionsInteractor(repository)
        getAddresses = object : GetAddresses() {
            override fun invoke(): List<String> = listOf("testAddress")
        }

        viewModel = HomeViewModel(balanceInteractor, transactionsInteractor, getAddresses)
    }

    @Test
    fun `addresses should emit updated balance`() = runTest {
        viewModel.addresses.test {
            assertNull(awaitItem())

            advanceTimeBy(10_000)

            val updatedBalances = awaitItem()
            assertNotNull(updatedBalances)
            assertEquals(1, updatedBalances.size)
            assertEquals(TestFixtures.dummyUtxoConfirmed.value, updatedBalances[0].balance)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onAddressSelected should update selectedAddress and fetch transactions`() = runTest {
        val address = BalanceByAddress("testAddress", TestFixtures.dummyUtxoConfirmed.value, listOf(TestFixtures.dummyUtxoConfirmed))

        viewModel.onAddressSelected(address)
        assertEquals(address, viewModel.selectedAddress)

        viewModel.transactions.test {
            assertNull(awaitItem())

            advanceTimeBy(10_000)

            val transactions = awaitItem()
            assertNotNull(transactions)
            assertEquals(1, transactions.size)
            assertEquals(TestFixtures.dummyTransactionHighValue.transactionId, transactions[0].transactionId)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onAddressSelected with null should reset selection and stop polling`() = runTest {
        val address = BalanceByAddress("testAddress", TestFixtures.dummyUtxoConfirmed.value, listOf(TestFixtures.dummyUtxoConfirmed))

        viewModel.onAddressSelected(address)
        assertEquals(address, viewModel.selectedAddress)

        viewModel.onAddressSelected(null)
        assertNull(viewModel.selectedAddress)
    }
}