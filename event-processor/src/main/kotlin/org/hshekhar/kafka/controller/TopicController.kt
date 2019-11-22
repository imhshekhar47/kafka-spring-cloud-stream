package org.hshekhar.kafka.controller

import org.apache.kafka.streams.state.QueryableStoreTypes
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore
import org.hshekhar.kafka.binding.BindingConstant
import org.hshekhar.kafka.model.SessionState
import org.hshekhar.kafka.model.Stock
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/kafka"])
class TopicController(private val queryService: InteractiveQueryService){

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(TopicController::class.java)
    }

    @GetMapping(value = ["/user/active"])
    fun getActiveSession(): List<SessionState> {
        LOGGER.trace("entry: getActiveSession()")
        val listOfUser = mutableListOf<SessionState>()
        val queryStoreType: ReadOnlyKeyValueStore<String, SessionState> =
                queryService.getQueryableStore(BindingConstant.MV_ACTIVE_SESSION, QueryableStoreTypes.keyValueStore())

        queryStoreType.all().forEach { kvPair ->
            val sessionState = kvPair.value
            if("active" == sessionState.state) {
                listOfUser.add(kvPair.value)
            }
        }

        LOGGER.trace("exit: getActiveSession(): ${listOfUser.size}")
        return listOfUser.sortedBy { state -> state.userId }
    }


    @GetMapping(value = ["/stock/ticker"])
    fun getLatestStockPrice(): List<Stock> {
        LOGGER.trace("entry: getLatestStockPrice()")
        val listOfStock = mutableListOf<Stock>()
        val queryStoreType : ReadOnlyKeyValueStore<String, Stock> =
                queryService.getQueryableStore(BindingConstant.MV_TRANSACTION, QueryableStoreTypes.keyValueStore())

        queryStoreType.all().forEach {
            listOfStock.add(it.value)
        }

        LOGGER.trace("exit: getLatestStockPrice()")

        return listOfStock.sortedBy { stock -> stock.id }
    }
}