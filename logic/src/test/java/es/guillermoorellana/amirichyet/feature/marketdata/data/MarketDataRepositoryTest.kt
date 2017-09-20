package es.guillermoorellana.amirichyet.feature.marketdata.data

import es.guillermoorellana.amirichyet.core.data.store.ReactiveStore
import es.guillermoorellana.amirichyet.service.bitcoindata.BitcoinDataRaw
import es.guillermoorellana.amirichyet.service.bitcoindata.MarketDataService
import io.reactivex.Single
import org.amshove.kluent.When
import org.amshove.kluent.`it returns`
import org.amshove.kluent.calling
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Strict::class)
class MarketDataRepositoryTest {
    @Mock
    private lateinit var store: ReactiveStore<String, MarketData>
    @Mock
    private lateinit var service: MarketDataService
    @Mock
    private lateinit var mapper: MarketDataMapper
    @Mock
    private lateinit var bitcoinDataRaw: BitcoinDataRaw


    @Test
    fun `I know how to configure tests`() {
        When calling service.getMarketPrice() `it returns` Single.just(bitcoinDataRaw)
        val repo = createRepo()

        val observer = repo.fetchMarketData().test()

    }

    private fun createRepo(): MarketDataRepository =
            MarketDataRepository(
                    store,
                    service,
                    mapper
            )
}
