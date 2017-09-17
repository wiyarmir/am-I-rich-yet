package es.guillermoorellana.amirichyet.marketdata

import dagger.Subcomponent
import es.guillermoorellana.amirichyet.feature.marketdata.MarketDataModule

@Subcomponent(modules = arrayOf(MarketDataModule::class))
interface MarketDataComponent {
    fun inject(marketChartFragment: MarketChartFragment)
}
