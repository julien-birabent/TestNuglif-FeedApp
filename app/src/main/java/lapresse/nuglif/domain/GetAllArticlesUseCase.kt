package lapresse.nuglif.domain

import io.reactivex.rxjava3.core.Flowable
import lapresse.nuglif.data.ArticleRepository
import lapresse.nuglif.ui.item.Article
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetAllArticlesUseCase : AsyncUseCase<Any?, List<Article>>(), KoinComponent {

    private val articleRepository: ArticleRepository by inject()

    override fun execute(params: Any?): Flowable<List<Article>> =
        articleRepository.getAllArticles()
            .map { ArticleModelMapper.map(it)}
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())

}