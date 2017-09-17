package es.guillermoorellana.amirichyet.feature.marketdata.data

import es.guillermoorellana.amirichyet.service.marketdata.MarketDataRaw

typealias MarketDataMapper = (MarketDataRaw) -> List<MarketData>

internal fun createMarketDataMapper(): MarketDataMapper =
        TODO()