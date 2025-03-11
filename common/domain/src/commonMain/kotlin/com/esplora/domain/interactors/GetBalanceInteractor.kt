package com.esplora.domain.interactors

import com.esplora.data.repository.EsploraRepository
import com.esplora.models.Balance

interface GetBalanceInteractor {
    suspend fun execute(address: String): Balance
}

class DefaultGetBalanceInteractor(private val repository: EsploraRepository) : GetBalanceInteractor {
    override suspend fun execute(address: String): Balance {
        return repository.getBalance(address)
    }
}