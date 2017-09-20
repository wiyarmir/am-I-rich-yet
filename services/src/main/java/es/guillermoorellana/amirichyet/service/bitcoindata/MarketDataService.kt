package es.guillermoorellana.amirichyet.service.bitcoindata

import io.reactivex.Single
import retrofit2.http.GET

interface MarketDataService {
    @GET("charts/market-price")
    fun getMarketPrice(): Single<BitcoinDataRaw>
}