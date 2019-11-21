package org.hshekhar.kafka.event

class PageViewEvent(val userId: String, val page: String, val duration: Int = 0)