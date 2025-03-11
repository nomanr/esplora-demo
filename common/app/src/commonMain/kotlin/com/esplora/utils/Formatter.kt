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
    fun formatAddress(address: String, prefixLength: Int = 12, suffixLength: Int = 12): String {
        return if (address.length > prefixLength + suffixLength) {
            "${address.take(prefixLength)}...${address.takeLast(suffixLength)}"
        } else {
            address
        }
    }
}