package lapresse.nuglif.domain

import io.reactivex.rxjava3.core.Flowable
import lapresse.nuglif.thread.SchedulerProvider
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

abstract class AsyncUseCase<in Params, Results : Any>(): KoinComponent {

    protected val schedulerProvider: SchedulerProvider by inject()

    abstract fun build(params: Params): Flowable<Results>

    fun execute(params: Params): Flowable<Results> = build(params)

}