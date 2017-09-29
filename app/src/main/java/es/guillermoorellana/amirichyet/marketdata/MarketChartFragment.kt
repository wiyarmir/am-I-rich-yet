package es.guillermoorellana.amirichyet.marketdata

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
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

    private val chart: LineChart by bindView(R.id.chart)
    private val dataSet: LineDataSet by lazy(ChartHelper.dataSetGenerator { resources })

    override fun onAttach(context: Context?) {
        activity.getComponent<MainActivityComponent>()
                .marketChartComponent()
                .inject(this)

        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(this, viewModelFactory).get(MarketChartViewModel::class.java)
        viewModel.marketGraphLiveData.observe(this, Observer { plotData -> ChartHelper.plotData(plotData, chart, dataSet) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ChartHelper.setupChart(chart)
    }

    private object ChartHelper {

        fun setupChart(chart: LineChart) {
            chart.setDrawGridBackground(false)
            chart.description.isEnabled = false
            chart.axisLeft.isEnabled = false

            setupXaxis(chart.xAxis)
            setupYaxis(chart.axisRight)
        }

        private fun setupXaxis(xAxis: XAxis) {
            xAxis.valueFormatter = object : IAxisValueFormatter {

                private val format = SimpleDateFormat("dd MMM HH:mm", Locale.UK)

                override fun getFormattedValue(value: Float, axis: AxisBase): String {
                    val millis = TimeUnit.SECONDS.toMillis(value.toLong())
                    return format.format(Date(millis))
                }
            }
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
        }

        private fun setupYaxis(yAxis: YAxis) {
            yAxis.setDrawGridLines(false)
        }

        fun plotData(plotData: MarketChartViewModel.PlotData?, chart: LineChart, dataSet: LineDataSet) =
                plotData?.let { (entries) ->
                    dataSet.values = entries
                }.also {
                    val lineData = LineData(dataSet)
                    chart.data = lineData
                }.also {
                    chart.invalidate()
                }

        fun dataSetGenerator(resources: () -> Resources): () -> LineDataSet = {
            val dataSet = LineDataSet(null, "Price")
            val accentColor = ResourcesCompat.getColor(resources(), R.color.accent, null)
            dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
            dataSet.cubicIntensity = .2f
            dataSet.setDrawCircles(false)
            dataSet.setDrawCircleHole(false)
            dataSet.color = accentColor
            dataSet.setDrawFilled(true)
            dataSet.fillColor = accentColor
            dataSet.fillAlpha = 255
            dataSet
        }
    }

    companion object {
        const val TAG: String = "MarketChartFragment"
    }
}
