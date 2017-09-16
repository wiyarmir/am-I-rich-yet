package es.guillermoorellana.amirichyet.core.data.store

import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.schedulers.Schedulers
import java.util.*

class MemoryReactiveStore<in Key, Value>(
        private val extractKeyFromModel: (Value) -> Key,
        private val cache: MemoryStore<Key, Value>
) : ReactiveStore<Key, Value> {

    private val allProcessor: FlowableProcessor<Optional<List<Value>>> = PublishProcessor.create<Optional<List<Value>>>().toSerialized()
    private val processorMap: MutableMap<Key, FlowableProcessor<Optional<Value>>> = hashMapOf()

    override fun store(model: Value) {
        val key = extractKeyFromModel(model)
        cache.put(model)
        getOrCreateSubjectForKey(key).onNext(Optional.of(model))
        // One item has been added/updated, notify to all as well
        val allValues = cache.getAll().map({ Optional.of(it) }).blockingGet(Optional.empty())
        allProcessor.onNext(allValues)
    }

    override fun storeAll(modelList: List<Value>) {
        cache.putAll(modelList)
        allProcessor.onNext(Optional.of(modelList))
        // Publish in all the existing single item streams.
        // This could be improved publishing only in the items that changed. Maybe use DiffUtils?
        publishInEachKey()
    }

    override fun replaceAll(modelList: List<Value>) {
        cache.clear()
        storeAll(modelList)
    }

    override fun get(key: Key): Flowable<Optional<Value>> {
        val model = cache.getSingular(key).map({ Optional.of(it) }).blockingGet(Optional.empty())
        return getOrCreateSubjectForKey(key).startWith(model)
                .observeOn(Schedulers.computation())
    }

    override fun getAll(): Flowable<Optional<List<Value>>> {
        val allValues = cache.getAll().map({ Optional.of(it) }).blockingGet(Optional.empty())
        return allProcessor.startWith(allValues)
                .observeOn(Schedulers.computation())
    }

    private fun getOrCreateSubjectForKey(key: Key): FlowableProcessor<Optional<Value>> {
        synchronized(processorMap) {
            return Optional.ofNullable(processorMap[key]).orElseGet({ createAndStoreNewSubjectForKey(key) })
        }
    }

    private fun createAndStoreNewSubjectForKey(key: Key): FlowableProcessor<Optional<Value>> {
        val processor = PublishProcessor.create<Optional<Value>>().toSerialized()
        synchronized(processorMap) {
            processorMap.put(key, processor)
        }
        return processor
    }

    /**
     * Publishes the cached data in each independent stream only if it exists already.
     */
    private fun publishInEachKey() {
        var keySet: Set<Key> = emptySet()
        synchronized(processorMap) {
            keySet = HashSet(processorMap.keys)
        }
        for (key in keySet) {
            val value = cache.getSingular(key).map({ Optional.of(it) }).blockingGet(Optional.empty())
            publishInKey(key, value)
        }
    }

    /**
     * Publishes the cached value if there is an already existing stream for the passed key. The case where there isn't a stream for the passed key
     * means that the data for this key is not being consumed and therefore there is no need to publish.
     */
    private fun publishInKey(key: Key, model: Optional<Value>) {
        var processor: FlowableProcessor<Optional<Value>>? = null
        synchronized(processorMap) {
            processor = processorMap[key]
        }
        processor?.apply { onNext(model) }
    }
}