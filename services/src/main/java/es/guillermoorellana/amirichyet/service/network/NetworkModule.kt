package es.guillermoorellana.amirichyet.service.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import es.guillermoorellana.amirichyet.core.injection.qualifier.NetworkInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiOkHttpClient(@NetworkInterceptor networkInterceptor: Set<@JvmSuppressWildcards Interceptor>): OkHttpClient =
            OkHttpClient.Builder().also {
                it.networkInterceptors().addAll(networkInterceptor)
            }.build()

    @Provides
    @Singleton
    fun provideRetrofit(@Named(API_URL) baseUrl: String, gson: Gson, client: OkHttpClient): Retrofit {
        return Retrofit.Builder().addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(baseUrl)
                .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson =
            GsonBuilder()
                    .create()

    companion object {
        const val API_URL = "API_URL"
    }
}