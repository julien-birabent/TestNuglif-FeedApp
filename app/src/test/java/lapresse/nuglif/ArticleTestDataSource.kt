package lapresse.nuglif

import io.reactivex.rxjava3.core.Flowable
import lapresse.nuglif.data.model.ArticleModel
import lapresse.nuglif.data.model.Visual
import lapresse.nuglif.data.source.DataSource

object ArticleTestDataSource : DataSource<ArticleModel> {

    val testArticlesOrdered = listOf(
        ArticleModel(
            "channel1",
            "no use",
            "no use",
            "1",
            "no use",
            "no use",
            "2018-01-18 12:01:00.000",
            "no use",
            "no use",
            "no use",
            listOf(Visual("","",""))
        ),
        ArticleModel(
            "channel2",
            "no use",
            "no use",
            "2",
            "no use",
            "no use",
            "2017-01-18 12:01:00.000",
            "no use",
            "no use",
            "no use",
            listOf(Visual("","",""))
        ),
        ArticleModel(
            "channel3",
            "no use",
            "no use",
            "3",
            "no use",
            "no use",
            "2016-01-18 12:01:00.000",
            "no use",
            "no use",
            "no use",
            listOf(Visual("","",""))
        )
    )

    override fun fetchData(): Flowable<List<ArticleModel>> = Flowable.just(testArticlesOrdered)
}