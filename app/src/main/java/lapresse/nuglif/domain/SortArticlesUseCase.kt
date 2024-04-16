package lapresse.nuglif.domain

import io.reactivex.rxjava3.core.Flowable
import lapresse.nuglif.ui.FeedSortOptions
import lapresse.nuglif.ui.model.Article

class SortArticlesUseCase :
    AsyncUseCase<Pair<List<Article>, FeedSortOptions>, List<Article>>() {

    override fun execute(params: Pair<List<Article>, FeedSortOptions>): Flowable<List<Article>> {
        return Flowable.just(params).map { (unsortedArticles, sortingOption) ->
            when (sortingOption) {
                FeedSortOptions.BY_CHANNEL -> unsortedArticles.sortedBy { it.channelName }
                FeedSortOptions.BY_PUBLICATION_DATE -> unsortedArticles.sortedByDescending { it.publicationDate }
            }
        }.subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.mainThread())
    }
}