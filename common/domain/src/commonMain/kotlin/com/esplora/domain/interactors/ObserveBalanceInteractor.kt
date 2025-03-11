package com.esplora.domain.interactors

import com.esplora.data.repository.EsploraRepository
import com.esplora.domain.PollingInteractor
import com.esplora.domain.testAddresses
import com.esplora.models.Balance
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.supervisorScope

class ObserveBalanceInteractor(
    private val repository: EsploraRepository
) : PollingInteractor<Unit, Map<String, Balance>>() {
    override fun createObservable(params: Unit): Flow<Map<String, Balance>> {
        return repository.observeAllBalances()
    }

    override suspend fun doWork(params: Unit) {
        supervisorScope {
            testAddresses.map {
                async { repository.fetchAndStoreBalance(it) }
            }.forEach { it.await() }
        }
    }
}


