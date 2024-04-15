package lapresse.nuglif.data.source

import io.reactivex.rxjava3.core.Flowable

interface DataSource<Data : Any> {

    fun fetchData(): Flowable<List<Data>>
}