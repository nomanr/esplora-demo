package com.esplora.data

import com.esplora.data.repository.DefaultEsploraRepository
import com.esplora.data.repository.EsploraRepository
import com.esplora.network.networkModule
import org.koin.dsl.module

val dataModule = module {
    includes(networkModule)

    single<EsploraRepository> {
        DefaultEsploraRepository(
            apiService = get()
        )
    }
}