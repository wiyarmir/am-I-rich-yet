package es.guillermoorellana.amirichyet.main

import android.arch.lifecycle.LifecycleActivity
import android.os.Bundle
import es.guillermoorellana.amirichyet.R
import es.guillermoorellana.amirichyet.marketdata.MarketChartFragment

class MainActivity : LifecycleActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .add(R.id.container, MarketChartFragment(), MarketChartFragment.TAG)
                .commit()
    }
}
