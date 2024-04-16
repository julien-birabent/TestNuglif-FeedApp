package lapresse.nuglif

import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import lapresse.nuglif.thread.SchedulerProvider

object TestSchedulerProvider : SchedulerProvider {
    override fun computation(): Scheduler = Schedulers.trampoline()

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun mainThread(): Scheduler = Schedulers.trampoline()
}