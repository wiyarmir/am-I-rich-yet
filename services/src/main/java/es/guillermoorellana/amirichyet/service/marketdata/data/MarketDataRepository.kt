package es.guillermoorellana.amirichyet.service.marketdata.data

import es.guillermoorellana.amirichyet.core.data.store.ReactiveStore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MarketDataRepository(
        private val store: ReactiveStore<String, MarketData>,
        private val marketDataService: MarketDataService,
        private val marketDataMapper: MarketDataMapper) {

    fun getAllMarketData(): Flowable<Optional<List<MarketData>>> = store.getAll()

    fun fetchMarketData(): Completable {
        return marketDataService.getMarketPrice()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                // map from raw to safe
                .map(marketDataMapper)
                // put mapped objects in store
                .doOnSuccess({ store.replaceAll(it) })
                .toCompletable()
    }
}