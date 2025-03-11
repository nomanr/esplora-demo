package com.esplora.domain

import com.esplora.data.dataModule
import com.esplora.domain.interactors.ObserveBalanceInteractor
import com.esplora.domain.interactors.ObserveTransactionsInteractor
import org.koin.dsl.module

val domainModule = module {
    includes(dataModule)

    factory { ObserveBalanceInteractor(repository = get()) }
    factory { ObserveTransactionsInteractor(repository = get()) }

}