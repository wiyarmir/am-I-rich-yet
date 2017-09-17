package es.guillermoorellana.amirichyet.feature.marketdata.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.guillermoorellana.amirichyet.feature.marketdata.data.MarketData
import es.guillermoorellana.amirichyet.feature.marketdata.interactor.RetrieveMarketData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MarketDataViewModel(
        private val retrieveMarketData: RetrieveMarketData
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable.add(bindToMarketData())
    }

    val marketGraphLiveData = MutableLiveData<PlotData>()
        get

    private val mapper: (List<MarketData>) -> PlotData = TODO()

    private fun bindToMarketData(): Disposable =
            retrieveMarketData.getStream(Optional.empty())
                    .observeOn(Schedulers.computation())
                    .map(mapper)
                    .subscribe({ marketGraphLiveData.postValue(it) })
}

class PlotData {}
