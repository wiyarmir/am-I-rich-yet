package es.guillermoorellana.amirichyet.core.provider

import javax.inject.Inject

class TimestampProvider @Inject constructor() {

    fun currentTimeMillis(): Long {
        return System.currentTimeMillis()
    }
}
