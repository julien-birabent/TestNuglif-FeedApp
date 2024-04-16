package lapresse.nuglif.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import io.reactivex.rxjava3.kotlin.Flowables
import io.reactivex.rxjava3.processors.BehaviorProcessor
import lapresse.nuglif.domain.GetAllArticlesUseCase
import lapresse.nuglif.domain.SortArticlesUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ArticleFeedViewModel : ViewModel(), KoinComponent {

    private val allArticlesUseCase: GetAllArticlesUseCase by inject()
    private val sortingUseCase = SortArticlesUseCase()

    private val sortFeedProcessor: BehaviorProcessor<FeedSortOptions> =
        BehaviorProcessor.createDefault(FeedSortOptions.BY_PUBLICATION_DATE)

    private val allArticles = Flowables.combineLatest(allArticlesUseCase.execute(Unit), sortFeedProcessor)
        .distinctUntilChanged { previous, new -> previous.second == new.second }
        .flatMap { (articles, sortOption) -> sortingUseCase.execute(articles to sortOption) }

    val articles = allArticles.toLiveData()

    fun sortFeedBy(options: FeedSortOptions) {
        sortFeedProcessor.onNext(options)
    }
}