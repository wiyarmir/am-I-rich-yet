package es.guillermoorellana.amirichyet.feature.marketdata.data

import es.guillermoorellana.amirichyet.core.data.store.ReactiveStore
import es.guillermoorellana.amirichyet.service.marketdata.MarketDataService
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarketDataRepository @Inject constructor(
        private val store: ReactiveStore<@JvmSuppressWildcards String, MarketData>,
        private val marketDataService: MarketDataService,
        private val marketDataMapper: MarketDataMapper
) {

    fun getAllMarketData(): Flowable<Optional<List<MarketData>>> = store.getAll()

    fun fetchMarketData(): Completable {
        return marketDataService.getMarketPrice()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                // map from raw to safe
                .map(marketDataMapper::map)
                // put mapped objects in store
                .doOnSuccess({ store.replaceAll(it) })
                .toCompletable()
    }
}