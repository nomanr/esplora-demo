package com.esplora.utils

import kotlin.math.roundToInt

object Formatter {
    fun formatBalance(totalBalance: Double): String {
        return if (totalBalance % 1.0 == 0.0) {
            "BTC ${totalBalance.toInt()}"
        } else {
            "BTC ${((totalBalance * 100).roundToInt() / 100.0)}"
        }
    }
    fun formatAddress(address: String, prefixLength: Int = 6, suffixLength: Int = 6): String {
        return if (address.length > prefixLength + suffixLength) {
            "${address.take(prefixLength)}...${address.takeLast(suffixLength)}"
        } else {
            address
        }
    }
}