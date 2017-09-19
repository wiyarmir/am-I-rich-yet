package es.guillermoorellana.amirichyet.application

import dagger.BindsInstance
import dagger.Component
import es.guillermoorellana.amirichyet.main.MainActivityComponent
import javax.inject.Singleton

@Singleton
@Component(
        modules = arrayOf(
                ApplicationModule::class,
                DataModule::class,
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
