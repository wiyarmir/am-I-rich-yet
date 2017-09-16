package es.guillermoorellana.amirichyet.core.data.store

import io.reactivex.Maybe

interface Store<in Key, Value> {

    fun put(value: Value)

    fun putAll(values: List<Value>)

    fun clear()

    fun getSingular(key: Key): Maybe<Value>

    fun getAll(): Maybe<List<Value>>
}

interface MemoryStore<in Key, Value> : Store<Key, Value>

interface DiskStore<in Key, Value> : Store<Key, Value>