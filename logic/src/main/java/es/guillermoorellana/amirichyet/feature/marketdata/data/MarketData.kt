package es.guillermoorellana.amirichyet.feature.marketdata.data

data class MarketData(
        val currency: String,
        val dataPoints: List<MarketDataPoint>
)

data class MarketDataPoint(
        val timestamp: Long,
        val value: Double
)
