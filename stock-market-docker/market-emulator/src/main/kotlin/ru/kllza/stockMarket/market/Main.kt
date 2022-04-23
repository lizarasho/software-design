package ru.kllza.stockMarket.market

import ru.kllza.stockMarket.repository.MongoStockRepository

/**
 * Usage: <port> <databaseConnectionString>
 */
fun main(args: Array<String>) =
    StockMarketService(
        MongoStockRepository(args[1])
    ).start(args[0].toInt())