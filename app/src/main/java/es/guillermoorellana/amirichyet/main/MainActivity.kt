package es.guillermoorellana.amirichyet.main

import android.arch.lifecycle.LifecycleActivity
import android.os.Bundle
import es.guillermoorellana.amirichyet.R
import es.guillermoorellana.amirichyet.application.getComponent
import es.guillermoorellana.amirichyet.injection.InjectingActivity
import es.guillermoorellana.amirichyet.marketdata.MarketChartFragment

class MainActivity : LifecycleActivity(), InjectingActivity<MainActivityComponent> {

    private lateinit var component: MainActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component = application.getComponent()
                .mainActivityComponent()

        component.inject(this)

        savedInstanceState?.apply {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, MarketChartFragment(), MarketChartFragment.TAG)
                    .commit()
        }
    }

    override fun getComponent(): MainActivityComponent = component
}
