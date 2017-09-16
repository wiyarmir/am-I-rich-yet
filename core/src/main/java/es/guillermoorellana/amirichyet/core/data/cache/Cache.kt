package es.guillermoorellana.amirichyet.core.data.cache

import es.guillermoorellana.amirichyet.core.data.store.MemoryStore
import es.guillermoorellana.amirichyet.core.provider.TimestampProvider
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ConcurrentHashMap


class Cache<Key, Value> : MemoryStore<Key, Value> {

    private val timestampProvider: TimestampProvider
    private val extractKeyFromModel: (Value) -> Key
    private val itemLifespanMs: Long?
    private val cache: MutableMap<Key, CacheEntry<Value>> = ConcurrentHashMap()

    constructor(extractKeyFromModel: (Value) -> Key,
                timestampProvider: TimestampProvider) : this(extractKeyFromModel, timestampProvider, null)

    private constructor(extractKeyFromModel: (Value) -> Key,
                        timestampProvider: TimestampProvider,
                        timeoutMs: Long?) {
        this.timestampProvider = timestampProvider
        this.itemLifespanMs = timeoutMs
        this.extractKeyFromModel = extractKeyFromModel
    }

    override fun put(value: Value) {
        Single.fromCallable({ extractKeyFromModel(value) })
                .subscribeOn(Schedulers.computation())
                .subscribe({ key -> cache.put(key, createCacheEntry(value)) })
    }

    override fun putAll(values: List<Value>) {
        Observable.fromIterable(values)
                .toMap(extractKeyFromModel, this::createCacheEntry)
                .subscribeOn(Schedulers.computation())
                .subscribe(Consumer(cache::putAll))
    }

    override fun getSingular(key: Key): Maybe<Value> {
        return Maybe.fromCallable<Boolean> { cache.containsKey(key) }
                .filter { isPresent -> isPresent }
                .map({ _ -> cache.getValue(key) })
                .filter({ this.notExpired(it) })
                .map({ it.cachedObject })
                .subscribeOn(Schedulers.computation())
    }

    override fun getAll(): Maybe<List<Value>> {
        return Observable.fromIterable(cache.values)
                .filter({ this.notExpired(it) })
                .map({ it.cachedObject })
                .toList()
                .filter({ it.isNotEmpty() })
                .subscribeOn(Schedulers.computation())
    }

    override fun clear() {
        cache.clear()
    }

    private fun createCacheEntry(value: Value): CacheEntry<Value> =
            CacheEntry(cachedObject = value, creationTimestamp = timestampProvider.currentTimeMillis())

    private fun notExpired(cacheEntry: CacheEntry<Value>): Boolean {
        return when (itemLifespanMs) {
            null -> true
            else -> cacheEntry.creationTimestamp + itemLifespanMs > timestampProvider.currentTimeMillis()
        }
    }
}

internal data class CacheEntry<out T>(
        val cachedObject: T,
        val creationTimestamp: Long
)