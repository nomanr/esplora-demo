package com.esplora.domain.interactors

import com.esplora.domain.testAddresses

abstract class GetAddresses {
    abstract operator fun invoke(): List<String>
}

class DefaultGetAddresses : GetAddresses() {
    override fun invoke(): List<String> {
        return testAddresses
    }
}