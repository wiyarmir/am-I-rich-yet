package es.guillermoorellana.amirichyet.injection

import android.app.Activity

interface InjectingActivity<out Component> {
    fun getComponent(): Component
}

fun <Component> Activity.getComponent(): Component = (this as InjectingActivity<Component>).getComponent()
