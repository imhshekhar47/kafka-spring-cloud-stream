package org.hshekhar.kafka.emitter

import kotlin.random.Random

class SeedData {
    companion object {
        val userIds = arrayOf("alfred", "anton", "aston", "angela", "alice", "bryan", "brandon", "billy", "caleb", "clive", "cynthea", "david", "dustan")
        val pageIds = arrayOf("login", "logout", "index", "home", "blog", "products", "documentation", "partners", "career", "about", "search", "contact")

        val stocks = arrayOf<Pair<String, Double>>(
                Pair("GOOGL", 1300.0),
                Pair("AMZN", 1700.0),
                Pair("MSFT", 150.0),
                Pair("CSCO", 50.0),
                Pair("WMT", 120.0),
                Pair("AAPL", 260.0),
                Pair("FB", 200.0),
                Pair("BABA", 180.0)
        )
    }

    private fun pickRandom(items: Array<String>): String {
        return items[Random.nextInt(0, items.size)]
    }

    fun pickRandomUserId(): String = pickRandom(userIds)

    fun pickRandomPageId(): String = pickRandom(pageIds)

    fun getRandomStock(): Pair<String, Double> {
        val stock = stocks[Random.nextInt(0, stocks.size)]
        val changedPrice = stock.second * ( 1 + Random.nextInt(-1,2) * Random.nextDouble(0.01, 0.05))
        return Pair(stock.first, String.format("%.2f", changedPrice).toDouble())
    }
}