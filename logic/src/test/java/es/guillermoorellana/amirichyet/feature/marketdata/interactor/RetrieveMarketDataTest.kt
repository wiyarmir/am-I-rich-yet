package es.guillermoorellana.amirichyet.feature.marketdata.interactor

import es.guillermoorellana.amirichyet.feature.marketdata.data.MarketData
import es.guillermoorellana.amirichyet.feature.marketdata.data.MarketDataRepository
import io.reactivex.Flowable
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.*
import java.util.Optional.empty

@RunWith(MockitoJUnitRunner.Strict::class)
class RetrieveMarketDataTest {
    @Mock
    private lateinit var repo: MarketDataRepository

    private val emptyData: Flowable<Optional<List<MarketData>>> = Flowable.just(empty())
    private val someData = Flowable.just(Optional.of(listOf(mock<MarketData>())))

    @Before
    fun before() {
    }

    @Test
    fun `when data is empty fetch is requested`() {
        When calling repo.getAllMarketData() `it returns` emptyData
        val interactor = createInteractor()

        interactor.getStream(empty()).test()

        Verify on repo that repo.fetchMarketData() was called
    }

    @Test
    fun `when there is data in repo no fetch is requested`() {
        When calling repo.getAllMarketData() `it returns` someData
        val interactor = createInteractor()

        interactor.getStream(empty()).test()

        VerifyNotCalled on repo that repo.fetchMarketData() was called
    }

    private fun createInteractor() = RetrieveMarketData(repo)
}
