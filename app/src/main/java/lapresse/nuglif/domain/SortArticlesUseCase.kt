package lapresse.nuglif.domain

import io.reactivex.rxjava3.core.Flowable
import lapresse.nuglif.ui.FeedSortOptions
import lapresse.nuglif.ui.item.ArticleFeedListItem
import org.koin.core.component.KoinComponent

class SortArticlesUseCase :
    AsyncUseCase<Pair<List<ArticleFeedListItem>, FeedSortOptions>, List<ArticleFeedListItem>>() {

    override fun execute(params: Pair<List<ArticleFeedListItem>, FeedSortOptions>): Flowable<List<ArticleFeedListItem>> {
        return Flowable.just(params).map { (unsortedArticles, sortingOption) ->
            when (sortingOption) {
                FeedSortOptions.BY_CHANNEL -> unsortedArticles.sortedBy { it.channelName }
                FeedSortOptions.BY_PUBLICATION_DATE -> unsortedArticles.sortedByDescending { it.publicationDate }
            }
        }.subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
    }
}