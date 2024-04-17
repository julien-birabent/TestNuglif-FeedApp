package lapresse.nuglif.domain

import lapresse.nuglif.data.model.ArticleDTO
import lapresse.nuglif.ui.model.Article

object ArticleModelMapper {

    fun map(articles: List<ArticleDTO>): List<Article> = articles.map { article ->
        with(article) {
            Article(
                channelName,
                title,
                lead,
                photoUrl = visual.first().urlPattern,
                publicationDate,
                id
            )
        }
    }
}