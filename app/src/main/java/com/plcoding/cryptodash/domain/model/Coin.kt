package com.plcoding.cryptodash.domain.model

import kotlin.math.absoluteValue

data class Coin(
    val id: String,
    val image: String,
    val name: String,
    val rank: Int,
    val symbol: String,
    val change24h: Double
) {
    fun getChangeDirection(): String = when {
        change24h > 0 -> "↑ ${"%.2f".format(change24h)}"
        change24h < 0 -> "↓ ${"%.2f".format(change24h.absoluteValue)}"
        else -> "→ 0.00"
    }
}
