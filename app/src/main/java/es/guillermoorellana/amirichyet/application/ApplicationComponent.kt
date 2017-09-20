package es.guillermoorellana.amirichyet.application

import dagger.BindsInstance
import dagger.Component
import es.guillermoorellana.amirichyet.main.MainActivityComponent
import es.guillermoorellana.amirichyet.service.network.NetworkModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = arrayOf(
                ApplicationModule::class,
                DataModule::class,
                NetworkModule::class,
                ViewModelModule::class
        )
)
interface ApplicationComponent {
    fun inject(app: BeingRichApplication)

    fun mainActivityComponent(): MainActivityComponent

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: BeingRichApplication): Builder

        fun build(): ApplicationComponent
    }
}
