package es.guillermoorellana.amirichyet.marketdata

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.design.widget.BaseTransientBottomBar
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import es.guillermoorellana.amirichyet.R
import es.guillermoorellana.amirichyet.injection.getComponent
import es.guillermoorellana.amirichyet.main.MainActivityComponent
import kotterknife.bindView
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MarketChartFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val textView: TextView by bindView(R.id.text)
    private val chart: LineChart by bindView(R.id.chart)

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
        chart.xAxis.valueFormatter = object : IAxisValueFormatter {

            private val format = SimpleDateFormat("dd MMM HH:mm", Locale.UK)

            override fun getFormattedValue(value: Float, axis: AxisBase): String {
                val millis = TimeUnit.SECONDS.toMillis(value.toLong())
                return format.format(Date(millis))
            }
        }
    }

    private fun updateGraph(plotData: MarketChartViewModel.PlotData?) {
        view?.also { Snackbar.make(it, "data!", BaseTransientBottomBar.LENGTH_SHORT).show() }
        textView.text = plotData.toString()
        plotData
                ?.let { (entries) -> LineDataSet(entries, "Price") }
                .let { dataSet -> chart.data = LineData(dataSet) }
    }

    companion object {
        const val TAG: String = "MarketChartFragment"
    }
}
