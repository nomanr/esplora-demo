package com.esplora.domain

import com.esplora.data.dataModule
import com.esplora.domain.interactors.DefaultGetBalanceInteractor
import com.esplora.domain.interactors.GetBalanceInteractor
import org.koin.dsl.module

val domainModule = module {
    includes(dataModule)

    factory<GetBalanceInteractor> { DefaultGetBalanceInteractor(repository = get()) }

}