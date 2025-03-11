package com.esplora

import com.esplora.domain.domainModule
import org.koin.dsl.module

val appModule = module {
    includes(domainModule)
}