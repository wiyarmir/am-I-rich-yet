package es.guillermoorellana.amirichyet.marketdata

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import es.guillermoorellana.amirichyet.feature.marketdata.data.MarketData
import es.guillermoorellana.amirichyet.feature.marketdata.interactor.RetrieveMarketData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class MarketChartViewModel @Inject constructor(
        private val retrieveMarketData: RetrieveMarketData
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val mapper: (List<MarketData>) -> PlotData =
            { PlotData(it.map { PlotData.DataPoint(it.timestamp, it.value) }) }
    val marketGraphLiveData = MutableLiveData<PlotData>()
        get

    init {
        compositeDisposable.add(bindToMarketData())
    }

    private fun bindToMarketData(): Disposable =
            retrieveMarketData.getStream(Optional.empty())
                    .observeOn(Schedulers.computation())
                    .map(mapper)
                    .subscribe({ marketGraphLiveData.postValue(it) })

    data class PlotData(val dataPoints: List<DataPoint>) {
        data class DataPoint(val x: Date, val y: Double)
    }
}
