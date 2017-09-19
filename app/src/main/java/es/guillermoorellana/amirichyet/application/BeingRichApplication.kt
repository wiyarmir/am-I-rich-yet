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

    fun getComponent(): ApplicationComponent = component ?:
            DaggerApplicationComponent.builder()
                    .application(this)
                    .build()
                    .also { component = it }
}

fun Application.asBeingRichApplication() = this as BeingRichApplication

fun Application.getComponent() = asBeingRichApplication().getComponent()
