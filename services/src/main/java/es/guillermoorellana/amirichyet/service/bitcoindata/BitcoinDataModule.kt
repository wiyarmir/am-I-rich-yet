package es.guillermoorellana.amirichyet.service.bitcoindata

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import es.guillermoorellana.amirichyet.core.injection.qualifier.NetworkInterceptor
import es.guillermoorellana.amirichyet.service.network.NetworkModule
import okhttp3.Interceptor
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton


@Module
class BitcoinDataModule {

    @Provides
    fun provideService(retrofit: Retrofit): MarketDataService =
            retrofit.create(MarketDataService::class.java)

    @Provides
    @Named(NetworkModule.API_URL)
    fun provideBaseUrl(): String {
        return PRODUCTION_BASE_URL
    }

    @Provides
    @NetworkInterceptor
    @IntoSet
    @Singleton
    fun provideJsonInterceptor(): Interceptor = Interceptor { chain ->
        chain.request()
                .url()
                .newBuilder()
                .addQueryParameter("format", "json")
                .build()
                .let { httpUrl ->
                    chain.request()
                            .newBuilder()
                            .url(httpUrl)
                            .build()
                }
                .let { request ->
                    chain.proceed(request)
                }
    }

    companion object {
        private const val PRODUCTION_BASE_URL = "https://blockchain.info/"
    }
}
