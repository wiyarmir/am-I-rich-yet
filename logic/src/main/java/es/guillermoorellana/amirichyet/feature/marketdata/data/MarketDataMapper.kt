package es.guillermoorellana.amirichyet.feature.marketdata.data

import es.guillermoorellana.amirichyet.service.bitcoindata.BitcoinDataRaw

interface MarketDataMapper {
    fun map(bitcoinDataRaw: BitcoinDataRaw): List<MarketData>
}
