package com.esplora.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esplora.domain.interactors.ObserveBalanceInteractor
import com.esplora.models.BalanceByAddress
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


class HomeViewModel(observeBalanceInteractor: ObserveBalanceInteractor) : ViewModel() {

    init {
        println("NOMAN>>>>>, STARTING OBSERVE BALANCE INTERACTOR")
        observeBalanceInteractor(viewModelScope, Unit)

    }

    val addresses: StateFlow<List<BalanceByAddress>?> = observeBalanceInteractor
        .observe()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)


    fun refresh() {
//        observeBalanceInteractor.(viewModelScope)
    }
}