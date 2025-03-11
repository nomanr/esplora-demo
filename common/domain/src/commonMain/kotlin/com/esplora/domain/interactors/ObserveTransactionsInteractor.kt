package com.esplora.domain.interactors

import com.esplora.data.repository.EsploraRepository
import com.esplora.domain.PollingInteractor
import com.esplora.domain.testAddresses
import com.esplora.models.BalanceByAddress
import com.esplora.models.Transaction
import com.esplora.models.Utxo
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.supervisorScope

class ObserveTransactionsInteractor(
    private val repository: EsploraRepository
) : PollingInteractor<ObserveTransactionsInteractor.Params, List<Transaction>?>() {
    override fun createObservable(params: Params): Flow<List<Transaction>?> {
        return repository.observeTransactionsByAddress(params.address)
    }

    override suspend fun doWork(params: Params) {
        repository.fetchAndStoreTransactions(params.address)
    }

    data class Params(val address: String)
}


