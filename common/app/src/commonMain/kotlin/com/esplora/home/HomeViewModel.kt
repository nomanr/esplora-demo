package com.esplora.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esplora.domain.interactors.GetAddresses
import com.esplora.domain.interactors.ObserveBalanceInteractor
import com.esplora.domain.interactors.ObserveTransactionsInteractor
import com.esplora.domain.testAddresses
import com.esplora.models.BalanceByAddress
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn


//TODO: Better UI States
class HomeViewModel(
    observeBalanceInteractor: ObserveBalanceInteractor,
    private val observeTransactionsInteractor: ObserveTransactionsInteractor,
    getAddresses: GetAddresses,
) : ViewModel() {
    init {
        observeBalanceInteractor(viewModelScope, ObserveBalanceInteractor.Params(getAddresses()))
    }

    val addresses: StateFlow<List<BalanceByAddress>?> =
        observeBalanceInteractor.observe().stateIn(viewModelScope, SharingStarted.Lazily, null)

    private var _selectedAddress: BalanceByAddress? by mutableStateOf(null)

    val selectedAddress: BalanceByAddress?
        get() = _selectedAddress


    val transactions = observeTransactionsInteractor.observe().stateIn(viewModelScope, SharingStarted.Lazily, null)


    fun onAddressSelected(address: BalanceByAddress?) {
        _selectedAddress = address
        if (address != null) {
            observeTransactionsInteractor(viewModelScope, ObserveTransactionsInteractor.Params(address.address))
        } else {
            observeTransactionsInteractor.stopPolling()
        }
    }


}