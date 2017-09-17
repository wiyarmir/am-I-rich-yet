package es.guillermoorellana.amirichyet.core.rx

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import org.reactivestreams.Publisher
import java.util.*

class UnwrapOptionalTransformer<T> : FlowableTransformer<Optional<T>, T> {

    override fun apply(upstream: Flowable<Optional<T>>): Publisher<T> =
            upstream.filter({ it.isPresent })
                    .map({ it.get() })

    companion object {
        fun <T> create(): UnwrapOptionalTransformer<T> = UnwrapOptionalTransformer()
    }
}