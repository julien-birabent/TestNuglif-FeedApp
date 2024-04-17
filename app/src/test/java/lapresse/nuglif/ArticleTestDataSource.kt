package lapresse.nuglif

import io.reactivex.rxjava3.core.Flowable
import lapresse.nuglif.data.model.ArticleDTO
import lapresse.nuglif.data.model.VisualDTO
import lapresse.nuglif.data.source.DataSource

object ArticleTestDataSource : DataSource<ArticleDTO> {

    val testArticlesOrdered = listOf(
        ArticleDTO(
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
            listOf(VisualDTO("","",""))
        ),
        ArticleDTO(
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
            listOf(VisualDTO("","",""))
        ),
        ArticleDTO(
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
            listOf(VisualDTO("","",""))
        )
    )

    override fun fetchData(): Flowable<List<ArticleDTO>> = Flowable.just(testArticlesOrdered)
}