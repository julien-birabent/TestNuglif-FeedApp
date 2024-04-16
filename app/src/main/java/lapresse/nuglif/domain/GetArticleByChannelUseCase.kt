package lapresse.nuglif.domain

import io.reactivex.rxjava3.core.Flowable
import lapresse.nuglif.ui.model.Article
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetArticleByChannelUseCase : AsyncUseCase<String, List<Article>>(),
    KoinComponent {

    private val getFeedListItemsUseCase: GetAllArticlesUseCase by inject()

    override fun execute(params: String): Flowable<List<Article>> =
        getFeedListItemsUseCase.execute(Unit).map {
            if(params.isEmpty()) it else it.filter { article -> article.channelName == params }
        }
}