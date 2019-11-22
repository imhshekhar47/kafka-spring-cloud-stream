package org.hshekhar.kafka.binding

internal class BindingConstant {
    companion object {
        // Page visit
        const val PAGE_VISIT_IN = "page-visit-in"
        const val PAGE_VISIT_OUT_SESSION_STATE = "page-visit-out-session"
        const val MV_ACTIVE_SESSION = "store-active-sessions"


        // Transaction
        const val TRANSACTION_IN = "transaction-in"
        const val TRANSACTION_OUT_STOCK = "transaction-out-stock"

        const val MV_TRANSACTION = "store-stock"
    }
}