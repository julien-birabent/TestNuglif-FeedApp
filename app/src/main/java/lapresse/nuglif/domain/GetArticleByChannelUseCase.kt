package lapresse.nuglif.domain

import io.reactivex.rxjava3.core.Flowable
import lapresse.nuglif.ui.item.ArticleFeedListItem
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetArticleByChannelUseCase : AsyncUseCase<String, List<ArticleFeedListItem>>(),
    KoinComponent {

    private val getFeedListItemsUseCase: GetAllArticlesUseCase by inject()

    override fun execute(params: String): Flowable<List<ArticleFeedListItem>> =
        getFeedListItemsUseCase.execute(Unit).map {
            it.filter { article -> article.channelName == params }
        }
}