package es.guillermoorellana.amirichyet.feature.marketdata.data

import dagger.Module
import dagger.Provides
import es.guillermoorellana.amirichyet.core.data.cache.Cache
import es.guillermoorellana.amirichyet.core.data.store.MemoryReactiveStore
import es.guillermoorellana.amirichyet.core.data.store.MemoryStore
import es.guillermoorellana.amirichyet.core.data.store.ReactiveStore
import es.guillermoorellana.amirichyet.core.provider.TimestampProvider
import es.guillermoorellana.amirichyet.service.marketdata.MarketDataRaw
import es.guillermoorellana.amirichyet.service.marketdata.MarketDataService
import io.reactivex.Single

@Module
abstract class MarketDataModule(
        val extractKeyFromModel: (MarketData) -> String = { value -> value.timestamp.toString() }
) {

    @Provides
    fun provideCache(timestampProvider: TimestampProvider): MemoryStore<String, MarketData> =
            Cache(extractKeyFromModel, timestampProvider)

    @Provides
    fun provideStore(cache: MemoryStore<@JvmSuppressWildcards String, MarketData>): ReactiveStore<String, MarketData> =
            MemoryReactiveStore(extractKeyFromModel, cache)

    @Provides
    fun provideService(): MarketDataService = object : MarketDataService {
        override fun getMarketPrice(): Single<MarketDataRaw> = TODO()
    }

    @Provides
    fun provideMarketDataMapper(): MarketDataMapper = TODO()
}
