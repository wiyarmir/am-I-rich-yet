package es.guillermoorellana.amirichyet.application

import android.app.Application
import android.support.annotation.CallSuper

class BeingRichApplication : Application() {

    private var component: ApplicationComponent? = null

    @CallSuper
    override fun onCreate() {
        super.onCreate()
        getComponent().inject(this)
    }

    fun getComponent(): ApplicationComponent {
        if (component == null) {
            component = DaggerApplicationComponent.builder().application(this).build()
        }
        return component!!
    }
}
