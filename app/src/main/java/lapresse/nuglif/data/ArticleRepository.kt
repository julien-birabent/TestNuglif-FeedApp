package lapresse.nuglif.data

import io.reactivex.rxjava3.core.Flowable
import lapresse.nuglif.data.model.ArticleModel
import lapresse.nuglif.data.source.DataSource

class ArticleRepository(private val dataSource : DataSource<ArticleModel>) {

    fun getAllArticles(): Flowable<List<ArticleModel>> = dataSource.fetchData()
}