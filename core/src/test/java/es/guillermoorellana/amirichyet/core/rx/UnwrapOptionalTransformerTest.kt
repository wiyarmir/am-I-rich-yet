package es.guillermoorellana.amirichyet.core.rx

import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import org.junit.Before
import org.junit.Test
import java.util.*


class UnwrapOptionalTransformerTest {
    private lateinit var transformer: UnwrapOptionalTransformer<Any>

    @Before
    fun before() {
        transformer = UnwrapOptionalTransformer()
    }


    @Test
    fun `check that nones get filtered`() {
        val source = Flowable.just(Optional.empty<Any>())

        val ts = TestSubscriber<Any>()
        transformer.apply(source).subscribe(ts)

        ts.assertNoValues()
    }

    @Test
    fun `check that somes get through`() {
        val any = Any()
        val source = Flowable.just(Optional.of(any))

        val ts = TestSubscriber<Any>()
        transformer.apply(source).subscribe(ts)

        ts.assertValue(any)
    }
}