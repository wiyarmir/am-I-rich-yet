package es.guillermoorellana.amirichyet.core.data.store

import es.guillermoorellana.amirichyet.core.RxSchedulerOverrideRule
import es.guillermoorellana.amirichyet.core.data.cache.Cache
import io.reactivex.Maybe
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers
import java.util.Optional.empty
import java.util.Optional.of

class MemoryReactiveStoreTest {
    private val cache: Cache<String, TestObject> = org.amshove.kluent.mock()

    private lateinit var reactiveStore: MemoryReactiveStore<String, TestObject>

    @get:Rule
    val overrideSchedulersRule = RxSchedulerOverrideRule()

    @Before
    fun setUp() {
        reactiveStore = MemoryReactiveStore({ obj: TestObject -> obj.id }, cache)
    }

    @Test
    fun `none is emmited when cache is empty`() {
        ArrangeBuilder().withEmptyCache()

        reactiveStore.get("ID1").test().assertValue(empty())
        reactiveStore.getAll().test().assertValue(empty())
    }

    @Test
    fun `last stored item is emitted after subscription`() {
        val model = TestObject("ID1")
        ArrangeBuilder().withCachedObject(model)
                .withCachedObjectList(listOf(model))

        reactiveStore.store(model)
        reactiveStore.get("ID1").test().assertValue(of(model))
    }

    @Test
    fun `streams emit when single object is stored`() {
        val list = createTestObjectList()
        val model = TestObject("ID1")
        ArrangeBuilder().withCachedObjectList(list)
                .withCachedObject(model)

        reactiveStore.store(model)

        reactiveStore.get("ID1").test().assertValue(of(model))
        reactiveStore.getAll().test().assertValue(of(list))
    }

    @Test
    fun `streams emit when list of objects is stored`() {
        val list = createTestObjectList()
        val model = TestObject("ID1")
        ArrangeBuilder().withCachedObjectList(list)
                .withCachedObject(model)

        reactiveStore.storeAll(list)

        reactiveStore.get("ID1").test().assertValue(of(model))
        reactiveStore.getAll().test().assertValue(of(list))
    }

    @Test
    fun `streams emit when list of objects is replaced`() {
        val list = createTestObjectList()
        val model = TestObject("ID1")
        ArrangeBuilder().withCachedObjectList(list)
                .withCachedObject(model)

        reactiveStore.replaceAll(list)

        reactiveStore.get("ID1").test().assertValue(of(model))
        reactiveStore.getAll().test().assertValue(of(list))
    }

    @Test
    fun `object is stored in cache`() {
        val model = TestObject("ID1")
        ArrangeBuilder().withCachedObjectList(listOf(model))

        reactiveStore.store(model)

        Verify that cache.put(model) was called
    }

    @Test
    fun `object list is stored in cache`() {
        val list = createTestObjectList()

        reactiveStore.storeAll(list)

        Verify that cache.putAll(list) was called
    }

    @Test
    fun `cache is cleared in replace all`() {
        val list = createTestObjectList()

        reactiveStore.replaceAll(list)

        Verify that cache.clear() was called
    }

    private fun createTestObjectList(): List<TestObject> =
            listOf(
                    TestObject("ID1"),
                    TestObject("ID2"),
                    TestObject("ID3")
            )

    private data class TestObject internal constructor(val id: String)

    private inner class ArrangeBuilder {

        fun withCachedObject(obj: TestObject): ArrangeBuilder {
            When calling cache.getSingular(obj.id) `it returns` Maybe.just(obj)
            return this
        }

        fun withCachedObjectList(objectList: List<TestObject>): ArrangeBuilder {
            When calling cache.getAll() `it returns` Maybe.just(objectList)
            return this
        }

        fun withEmptyCache(): ArrangeBuilder {
            When calling cache.getSingular(ArgumentMatchers.anyString()) `it returns` Maybe.empty()
            When calling cache.getAll() `it returns` Maybe.empty()
            return this
        }
    }
}
