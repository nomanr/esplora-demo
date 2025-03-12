package com.esplora.domain

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class PollingInteractor<P : Any, T>(
    private val pollingInterval: Long = 10_000L
) {
    private val paramState = MutableStateFlow<P?>(null)
    private var pollingJob: Job? = null

    operator fun invoke(scope: CoroutineScope, params: P) {
        paramState.value = params
        startPolling(scope, params)
    }

    protected abstract fun createObservable(params: P): Flow<T>

    protected abstract suspend fun doWork(params: P)

    fun observe(): Flow<T> = paramState.filterNotNull().flatMapLatest { createObservable(it) }

    fun refetch(scope: CoroutineScope) {
        paramState.value?.let { params ->
            scope.launch { doWork(params) }
        }
    }

    private fun startPolling(scope: CoroutineScope, params: P) {
        pollingJob?.cancel()
        pollingJob = scope.launch {
            while (isActive) {
                try {
                    doWork(params)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                delay(pollingInterval)
            }
        }
    }

    fun stopPolling() {
        pollingJob?.cancel()
    }
}