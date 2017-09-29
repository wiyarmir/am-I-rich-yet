package es.guillermoorellana.amirichyet.marketdata

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.mikephil.charting.data.Entry
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
            { PlotData(it.map { Entry(it.timestamp.toFloat(), it.value.toFloat()) }) }

    val marketGraphLiveData = MutableLiveData<PlotData>()

    init {
        compositeDisposable.add(bindToMarketData())
    }

    private fun bindToMarketData(): Disposable =
            retrieveMarketData.getStream(Optional.empty())
                    .observeOn(Schedulers.computation())
                    .map(mapper)
                    .subscribe({ marketGraphLiveData.postValue(it) })

    data class PlotData(val entries: List<Entry>)
}
