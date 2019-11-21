package org.hshekhar.kafka.binding

internal class BindingConstant {
    companion object {
        // Page visit
        const val PAGE_VISIT_IN = "page-visit-in"
        const val PAGE_VISIT_OUT_SESSION_STATE = "page-visit-out-session"
        const val MV_ACTIVE_SESSION = "store-active-sessions"


        // User sessions
        const val SESSION_COUNT_IN = "session-count-in"
        const val SESSION_COUNT_OUT = "session-count-out"

        const val MV_SESSION_COUNT = "store-user-count"
    }
}