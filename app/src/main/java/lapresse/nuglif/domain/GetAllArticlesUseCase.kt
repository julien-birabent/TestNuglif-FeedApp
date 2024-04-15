package lapresse.nuglif.domain

import io.reactivex.rxjava3.core.Flowable
import lapresse.nuglif.data.ArticleRepository
import lapresse.nuglif.ui.item.ArticleFeedListItem
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetAllArticlesUseCase : AsyncUseCase<Any?, List<ArticleFeedListItem>>(), KoinComponent {

    private val articleRepository: ArticleRepository by inject()

    override fun build(params: Any?): Flowable<List<ArticleFeedListItem>> =
        articleRepository.getAllArticles()
            .map { ArticleModelMapper.map(it)}
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())

}