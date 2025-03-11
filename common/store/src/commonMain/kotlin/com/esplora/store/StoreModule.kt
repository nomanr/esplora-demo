package com.esplora.store

import com.esplora.store.local.DefaultEsploraLocalDataSource
import com.esplora.store.local.EsploraLocalDataSource
import org.koin.dsl.module

val storeModule = module {
    single<EsploraLocalDataSource> { DefaultEsploraLocalDataSource() }
}