package com.esplora.domain

import com.esplora.data.dataModule
import com.esplora.domain.interactors.ObserveBalanceInteractor
import org.koin.dsl.module

val domainModule = module {
    includes(dataModule)

    factory<ObserveBalanceInteractor> { ObserveBalanceInteractor(repository = get()) }

}