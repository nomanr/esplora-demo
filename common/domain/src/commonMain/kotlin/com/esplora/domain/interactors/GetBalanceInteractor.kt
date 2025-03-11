package com.esplora.domain.interactors

import com.esplora.data.repository.EsploraRepository
import com.esplora.network.models.NetworkBalanceResponse

interface GetBalanceInteractor {
    suspend fun execute(address: String): NetworkBalanceResponse
}

class DefaultGetBalanceInteractor(private val repository: EsploraRepository) : GetBalanceInteractor {
    override suspend fun execute(address: String): NetworkBalanceResponse {
        return repository.getBalance(address)
    }
}