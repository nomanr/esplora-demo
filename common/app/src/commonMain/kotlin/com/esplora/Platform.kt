package com.esplora

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform