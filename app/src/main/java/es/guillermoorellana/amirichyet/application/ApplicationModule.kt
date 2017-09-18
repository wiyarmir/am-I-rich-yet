package es.guillermoorellana.amirichyet.application

import android.content.Context

import dagger.Module
import dagger.Provides
import es.guillermoorellana.amirichyet.core.injection.qualifier.ForApplication

@Module
class ApplicationModule {
    @ForApplication
    @Provides
    fun provideApplicationContext(app: BeingRichApplication): Context = app.applicationContext
}
