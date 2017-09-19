package es.guillermoorellana.amirichyet.application

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import es.guillermoorellana.amirichyet.AppViewModelFactory
import es.guillermoorellana.amirichyet.injection.ViewModelKey
import es.guillermoorellana.amirichyet.marketdata.MarketChartViewModel

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MarketChartViewModel::class)
    abstract fun bindMarketChartViewModel(marketChartViewModel: MarketChartViewModel): ViewModel

    @Binds
    abstract fun bindViewModelProviderFactory(appViewModelFactory: AppViewModelFactory): ViewModelProvider.Factory
}
