package es.guillermoorellana.amirichyet.core.domain.interactor

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

interface ReactiveInteractor {

    /**
     * Sends changes to data layer.
     * It returns a [Single] that will emit the result of the send operation.
     *
     * @param <Result> the type of the send operation result.
     * @param <Params> required parameters for the send.
     */
    interface SendInteractor<Params, Result> : ReactiveInteractor {
        fun getSingle(params: Optional<Params>): Single<Result>
    }

    /**
     * Retrieves changes from the data layer.
     * It returns an [Flowable] that emits updates for the retrieved object. The returned [Flowable] will never complete, but it can
     * error if there are any problems performing the required actions to serve the data.
     *
     * @param <Object> the type of the retrieved object.
     * @param <Params> required parameters for the retrieve operation.
     */
    interface RetrieveInteractor<Params, Object> : ReactiveInteractor {
        fun getStream(params: Optional<Params>): Flowable<Object>
    }

    /**
     * The request interactor is used to request some result once. The returned observable is a single, emits once and then completes or errors.
     *
     * @param <Params> the type of the returned data.
     * @param <Result> required parameters for the request.
     */
    interface RequestInteractor<Params, Result> : ReactiveInteractor {
        fun getSingle(params: Optional<Params>): Single<Result>
    }

    /**
     * The delete interactor is used to delete entities from data layer. The response for the delete operation comes as onNext
     * event in the returned observable.
     *
     * @param <Result> the type of the delete response.
     * @param <Params>   required parameters for the delete.
     */
    interface DeleteInteractor<Params, Result> : ReactiveInteractor {
        fun getSingle(params: Optional<Params>): Single<Result>
    }

    /**
     * The refresh interactor is used to refresh the reactive store with new data. Typically calling this interactor will trigger events in its
     * get interactor counterpart. The returned observable will complete when the refresh is finished or error if there was any problem in the process.
     *
     * @param <Params> required parameters for the refresh.
     */
    interface RefreshInteractor<Params> : ReactiveInteractor {
        fun getRefreshSingle(params: Optional<Params>): Completable
    }
}
