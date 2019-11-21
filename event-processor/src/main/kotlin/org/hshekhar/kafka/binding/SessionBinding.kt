package org.hshekhar.kafka.binding

import org.apache.kafka.streams.kstream.KStream
import org.hshekhar.kafka.model.PageVisit

interface SessionBinding {


    fun sessionInStream(): KStream<String, PageVisit>
}