package com.esplora.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esplora.domain.interactors.GetBalanceInteractor
import com.esplora.network.models.NetworkBalanceResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeViewModel(private val getBalanceInteractor: GetBalanceInteractor) : ViewModel() {

    private val _balanceState = MutableStateFlow<NetworkBalanceResponse?>(null)
    val balanceState: StateFlow<NetworkBalanceResponse?> = _balanceState.asStateFlow()

    fun fetchBalance(address: String) {
        viewModelScope.launch {
            try {
                _balanceState.value = getBalanceInteractor.execute(address)
            } catch (e: Exception) {
                e.printStackTrace() // Handle errors properly in production
            }
        }
    }
}