package es.guillermoorellana.amirichyet.marketdata

import dagger.Subcomponent

@Subcomponent(modules = arrayOf())
interface MarketChartComponent {
    fun inject(marketChartFragment: MarketChartFragment)
}
