package es.guillermoorellana.amirichyet.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import es.guillermoorellana.amirichyet.R
import es.guillermoorellana.amirichyet.application.getComponent
import es.guillermoorellana.amirichyet.injection.InjectingActivity
import es.guillermoorellana.amirichyet.marketdata.MarketChartFragment

class MainActivity : AppCompatActivity(), InjectingActivity<MainActivityComponent> {

    private lateinit var component: MainActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component = application.getComponent()
                .mainActivityComponent()
                .also { it.inject(this) }

        savedInstanceState ?: also {
            supportFragmentManager.beginTransaction()
                    .add(R.id.container, MarketChartFragment(), MarketChartFragment.TAG)
                    .commit()
        }
    }

    override fun getComponent(): MainActivityComponent = component
}
