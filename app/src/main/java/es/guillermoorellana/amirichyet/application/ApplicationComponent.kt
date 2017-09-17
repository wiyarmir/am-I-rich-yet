package es.guillermoorellana.amirichyet.application

import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component()
interface ApplicationComponent {
    fun inject(app: BeingRichApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: BeingRichApplication): Builder

        fun build(): ApplicationComponent
    }
}
