package es.guillermoorellana.amirichyet.core.data.store

import io.reactivex.Flowable
import java.util.*

interface ReactiveStore<in Key, Value> {

    fun store(model: Value)

    fun storeAll(modelList: List<Value>)

    fun replaceAll(modelList: List<Value>)

    fun get(key: Key): Flowable<Optional<Value>>

    fun getAll(): Flowable<Optional<List<Value>>>
}