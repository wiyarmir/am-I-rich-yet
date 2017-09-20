package es.guillermoorellana.amirichyet.injection

import android.app.Activity

interface InjectingActivity<out Component> {
    val component: Component
}

@Suppress("UNCHECKED_CAST")
fun <Component> Activity.getComponent(): Component = (this as InjectingActivity<Component>).component
