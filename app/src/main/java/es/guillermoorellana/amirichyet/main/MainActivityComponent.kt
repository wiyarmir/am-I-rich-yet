package es.guillermoorellana.amirichyet.main

import dagger.Subcomponent
import es.guillermoorellana.amirichyet.core.injection.scope.ActivityScope
import es.guillermoorellana.amirichyet.injection.ActivityModule

@ActivityScope
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface MainActivityComponent {
    fun inject(mainActivity: MainActivity)
}
