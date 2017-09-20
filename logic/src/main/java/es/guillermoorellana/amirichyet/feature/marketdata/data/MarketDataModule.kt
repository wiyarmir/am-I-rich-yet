package es.guillermoorellana.amirichyet.feature.marketdata.data

import dagger.Module
import dagger.Provides
import es.guillermoorellana.amirichyet.core.data.cache.Cache
import es.guillermoorellana.amirichyet.core.data.store.MemoryReactiveStore
import es.guillermoorellana.amirichyet.core.data.store.MemoryStore
import es.guillermoorellana.amirichyet.core.data.store.ReactiveStore
import es.guillermoorellana.amirichyet.core.provider.TimestampProvider
import es.guillermoorellana.amirichyet.service.bitcoindata.BitcoinDataModule
import es.guillermoorellana.amirichyet.service.bitcoindata.BitcoinDataRaw
import javax.inject.Singleton

@Module(includes = arrayOf(BitcoinDataModule::class))
class MarketDataModule {
    val extractKeyFromModel: (MarketData) -> String = { value -> value.timestamp.toString() }

    @Provides
    @Singleton
    fun provideCache(timestampProvider: TimestampProvider): MemoryStore<String, MarketData> =
            Cache(extractKeyFromModel, timestampProvider)

    @Provides
    @Singleton
    fun provideStore(cache: MemoryStore<@JvmSuppressWildcards String, MarketData>): ReactiveStore<String, MarketData> =
            MemoryReactiveStore(extractKeyFromModel, cache)

    @Provides
    fun provideMarketDataMapper(): MarketDataMapper = object : MarketDataMapper {
        override fun map(bitcoinDataRaw: BitcoinDataRaw): List<MarketData> =
                bitcoinDataRaw.values.map { MarketData(it.x, it.y) }
    }
}
