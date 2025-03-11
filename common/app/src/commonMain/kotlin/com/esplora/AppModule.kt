package com.esplora

import com.esplora.domain.domainModule
import com.esplora.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    includes(domainModule)

    viewModel { HomeViewModel(get()) }
}