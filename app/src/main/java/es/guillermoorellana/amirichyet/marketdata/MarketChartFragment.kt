package es.guillermoorellana.amirichyet.marketdata

import android.arch.lifecycle.LifecycleFragment
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.guillermoorellana.amirichyet.R
import es.guillermoorellana.amirichyet.injection.getComponent
import es.guillermoorellana.amirichyet.main.MainActivityComponent
import javax.inject.Inject


class MarketChartFragment : LifecycleFragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context?) {
        activity.getComponent<MainActivityComponent>()
                .marketChartComponent()
                .inject(this)

        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(MarketChartViewModel::class.java)
        viewModel.marketGraphLiveData.observe(this, Observer { updateGraph(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun updateGraph(plotData: MarketChartViewModel.PlotData?) {
        view?.also { Snackbar.make(it, "data!", BaseTransientBottomBar.LENGTH_SHORT) }
    }

    companion object {
        const val TAG: String = "MarketChartFragment"
    }
}
