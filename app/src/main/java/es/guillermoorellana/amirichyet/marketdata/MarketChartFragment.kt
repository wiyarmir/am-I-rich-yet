package es.guillermoorellana.amirichyet.marketdata

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.guillermoorellana.amirichyet.R
import es.guillermoorellana.amirichyet.feature.marketdata.presentation.MarketDataViewModel
import es.guillermoorellana.amirichyet.feature.marketdata.presentation.PlotData
import javax.inject.Inject


class MarketChartFragment : LifecycleFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(MarketDataViewModel::class.java)
        viewModel.marketGraphLiveData.observe(this, Observer { updateGraph(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun updateGraph(plotData: PlotData?) {

    }

    companion object {
        const val TAG: String = "MarketChartFragment"
    }
}
