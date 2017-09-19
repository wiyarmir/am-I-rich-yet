package es.guillermoorellana.amirichyet.application

import dagger.Module
import es.guillermoorellana.amirichyet.feature.marketdata.data.MarketDataModule

@Module(includes = arrayOf(MarketDataModule::class))
class DataModule {
}