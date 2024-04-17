package lapresse.nuglif.data

import io.reactivex.rxjava3.core.Flowable
import lapresse.nuglif.data.model.ArticleDTO
import lapresse.nuglif.data.source.DataSource

class ArticleRepository(private val dataSource : DataSource<ArticleDTO>) {

    fun getAllArticles(): Flowable<List<ArticleDTO>> = dataSource.fetchData()
}