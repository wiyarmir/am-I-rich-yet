package es.guillermoorellana.amirichyet.injection

import android.arch.lifecycle.LifecycleActivity
import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

import dagger.Module
import dagger.Provides
import es.guillermoorellana.amirichyet.core.injection.qualifier.ForActivity

@Module
class ActivityModule(
        private val activity: LifecycleActivity
) {

    @ForActivity
    @Provides
    fun provideContext(): Context = activity

    @Provides
    fun provideFragmentManager(activity: AppCompatActivity): FragmentManager =
            activity.supportFragmentManager
}
