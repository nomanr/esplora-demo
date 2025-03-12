package com.esplora.domain.interactors

import com.esplora.data.repository.EsploraRepository
import com.esplora.domain.PollingInteractor
import com.esplora.models.BalanceByAddress
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.supervisorScope

class ObserveBalanceInteractor(
    private val repository: EsploraRepository
) : PollingInteractor<ObserveBalanceInteractor.Params, List<BalanceByAddress>>() {

    override fun createObservable(params: Params): Flow<List<BalanceByAddress>> {
        return repository.observeAllBalances()
    }

    override suspend fun doWork(params: Params) {
        supervisorScope {
            params.addresses.map {
                async { repository.fetchAndStoreBalance(it) }
            }.forEach { it.await() }
        }
    }
    data class Params(val addresses: List<String>)

}


