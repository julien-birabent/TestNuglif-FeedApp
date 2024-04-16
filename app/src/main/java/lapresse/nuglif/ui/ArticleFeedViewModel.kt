package lapresse.nuglif.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import io.reactivex.rxjava3.kotlin.Flowables
import io.reactivex.rxjava3.kotlin.combineLatest
import io.reactivex.rxjava3.processors.BehaviorProcessor
import lapresse.nuglif.domain.GetAllArticlesUseCase
import lapresse.nuglif.domain.GetArticleByChannelUseCase
import lapresse.nuglif.domain.SortArticlesUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ArticleFeedViewModel : ViewModel(), KoinComponent {

    private val allArticlesUseCase: GetAllArticlesUseCase by inject()
    private val sortingUseCase = SortArticlesUseCase()
    private val filterUseCase = GetArticleByChannelUseCase()

    private val sortFeedProcessor: BehaviorProcessor<FeedSortOptions> =
        BehaviorProcessor.createDefault(FeedSortOptions.BY_PUBLICATION_DATE)

    private val channelNameFilterProcessor: BehaviorProcessor<String> =
        BehaviorProcessor.createDefault("")

    private val allArticlesProcessed = Flowables.combineLatest(channelNameFilterProcessor, sortFeedProcessor)
        .flatMap { (channelFilter, _) -> filterUseCase.execute(channelFilter)}
        .combineLatest(sortFeedProcessor)
        .flatMap { (filteredArticles, sortOption) -> sortingUseCase.execute(filteredArticles to sortOption) }
        .distinctUntilChanged { previous, new -> previous == new}

    val articles = allArticlesProcessed.toLiveData()

    val allChannels = allArticlesUseCase.execute(Unit)
        .map { articles -> articles.map { it.channelName }.toSet().toList() }
        .toLiveData()

    fun sortFeedBy(options: FeedSortOptions) {
        sortFeedProcessor.onNext(options)
    }

    fun filterSelectionByChannelName(channel: String) {
        channelNameFilterProcessor.onNext(channel)
    }
}