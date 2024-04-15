package lapresse.nuglif.domain

import lapresse.nuglif.data.model.ArticleModel
import lapresse.nuglif.ui.item.ArticleFeedListItem

object ArticleModelMapper {

    fun map(articles: List<ArticleModel>): List<ArticleFeedListItem> = articles.map { article ->
        with(article) {
            ArticleFeedListItem(
                channelName,
                title,
                lead,
                photoUrl = visual.first().urlPattern,
                publicationDate
            )
        }
    }
}