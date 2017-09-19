package es.guillermoorellana.amirichyet.feature.marketdata.data

import es.guillermoorellana.amirichyet.service.marketdata.MarketDataRaw

interface MarketDataMapper {
    fun map(marketDataRaw: MarketDataRaw): List<MarketData>
}
