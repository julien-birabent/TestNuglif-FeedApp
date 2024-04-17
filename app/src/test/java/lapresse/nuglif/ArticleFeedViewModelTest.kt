package lapresse.nuglif

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.LiveData
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.reactivex.rxjava3.processors.PublishProcessor
import io.reactivex.rxjava3.subscribers.TestSubscriber
import lapresse.nuglif.ArticleTestDataSource.testArticlesOrdered
import lapresse.nuglif.app.ResultState
import lapresse.nuglif.app.preferences.AppPreferences
import lapresse.nuglif.domain.ArticleModelMapper
import lapresse.nuglif.domain.GetAllArticlesUseCase
import lapresse.nuglif.domain.GetArticleByChannelUseCase
import lapresse.nuglif.domain.SortArticlesUseCase
import lapresse.nuglif.ui.FeedSortOptions
import lapresse.nuglif.ui.model.Article
import lapresse.nuglif.ui.viewmodel.ArticleFeedViewModel
import org.junit.After
import org.junit.AfterClass
import org.junit.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest

class ArticleFeedViewModelTest : KoinTest {

    companion object {
        @JvmStatic
        @BeforeClass
        fun mockMainLooper() {
            mockkStatic(Looper::class)

            val looper = mockk<Looper> {
                every { thread } returns Thread.currentThread()
            }

            every { Looper.getMainLooper() } returns looper
        }

        @JvmStatic
        @AfterClass
        fun unMock() {
            unmockkAll()
        }
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val lifecycleOwner = TestLifecycleOwner()
    private val appPreferencesMock: AppPreferences = mockk(relaxed = true)
    private val getAllArticlesUseCase: GetAllArticlesUseCase = mockk()
    private val sortArticlesUseCase: SortArticlesUseCase = mockk()
    private val getArticleByChannelUseCase: GetArticleByChannelUseCase = mockk()

    private val viewModel by lazy { ArticleFeedViewModel(appPreferencesMock) }


    @Before
    fun setup() {
        startKoin {
            modules(testModule)
        }
        lifecycleOwner.onResume()
    }

    @After
    fun doAfter() {
        lifecycleOwner.onDestroy()
        stopKoin()
    }

    @Test
    fun `GIVEN (the id of an existing article) WHEN (getArticleById is called) THEN(success is emitted and the right article is returned)`() {

        //GIVEN
        val useCaseProcessor = PublishProcessor.create<List<Article>>()
        val articles = ArticleModelMapper.map(testArticlesOrdered)
        val articleWanted = articles.first()
        every { getAllArticlesUseCase.execute(any()) } returns useCaseProcessor
        every { appPreferencesMock.sortingOptionSelected } returns FeedSortOptions.BY_PUBLICATION_DATE
        every { appPreferencesMock.channelFilterSelected } returns ""

        //WHEN
        val testSubscriber = viewModel.getArticleById(articleWanted.id).test(lifecycleOwner)
        useCaseProcessor.onNext(articles)

        //THEN
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertValue { results ->
            results is ResultState.Success && results.resultData.id == articleWanted.id
        }
    }

    @Test
    fun `GIVEN (a non valid article id) WHEN (getArticleById is called) THEN(error is emitted)`() {

        //GIVEN
        val useCaseProcessor = PublishProcessor.create<List<Article>>()
        val articles = ArticleModelMapper.map(testArticlesOrdered)
        val articleIdWanted = "-1"
        every { getAllArticlesUseCase.execute(any()) } returns useCaseProcessor
        every { appPreferencesMock.sortingOptionSelected } returns FeedSortOptions.BY_PUBLICATION_DATE
        every { appPreferencesMock.channelFilterSelected } returns ""

        //WHEN
        val testSubscriber = viewModel.getArticleById(articleIdWanted).test(lifecycleOwner)
        useCaseProcessor.onNext(articles)

        //THEN
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertValue { results ->
            results is ResultState.Error
        }
    }

    @Test
    fun `GIVEN (a list of articles) WHEN (all channels are observed) THEN(a set of channel corresponding to the list of articles is emitted)`() {

        //GIVEN
        val useCaseProcessor = PublishProcessor.create<List<Article>>()
        val testArticles = ArticleModelMapper.map(testArticlesOrdered)
        val channelsGiven = testArticles.map { it.channelName }.toSet()
        every { getAllArticlesUseCase.execute(any()) } returns useCaseProcessor
        every { appPreferencesMock.sortingOptionSelected } returns FeedSortOptions.BY_PUBLICATION_DATE
        every { appPreferencesMock.channelFilterSelected } returns ""

        //WHEN
        val testSubscriber = viewModel.allChannels.test(lifecycleOwner)
        useCaseProcessor.onNext(testArticles)

        //THEN
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertValue { results ->
            results.containsAll(channelsGiven)
            results.count() == channelsGiven.count()
        }
    }

    @Test
    fun `GIVEN (a channel name that has corresponding articles) WHEN (filterSelectionByChannelName is called) THEN(a list of articles exclusively associated to that channel is emitted)`() {

        //GIVEN
        val useCaseProcessor = PublishProcessor.create<List<Article>>()
        val testArticles = ArticleModelMapper.map(testArticlesOrdered)
        val testChannel = testArticles.first().channelName
        val numberOfArticlesForTestChannel = testArticles.count { it.channelName == testChannel }
        every { getAllArticlesUseCase.execute(any()) } returns useCaseProcessor
        every { appPreferencesMock.sortingOptionSelected } returns FeedSortOptions.BY_PUBLICATION_DATE
        every { appPreferencesMock.channelFilterSelected } returns testChannel

        //WHEN
        val testSubscriber = viewModel.articles.test(lifecycleOwner)
        useCaseProcessor.onNext(testArticles)

        //THEN
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertValue { results ->
            numberOfArticlesForTestChannel == results.count() && results.all { it.channelName == testChannel }
        }
    }

    @Test
    fun `GIVEN (a channel name that has no corresponding articles) WHEN (filterSelectionByChannelName is called) THEN(an empty list is emitted)`() {

        //GIVEN
        val useCaseProcessor = PublishProcessor.create<List<Article>>()
        val testArticles = ArticleModelMapper.map(testArticlesOrdered)
        val testChannel = "thiswillnotexistingnoteveninamillionyearsandyetitdoes"
        val numberOfArticlesForTestChannel = testArticles.count { it.channelName == testChannel }
        every { getAllArticlesUseCase.execute(any()) } returns useCaseProcessor
        every { appPreferencesMock.sortingOptionSelected } returns FeedSortOptions.BY_PUBLICATION_DATE
        every { appPreferencesMock.channelFilterSelected } returns testChannel

        //WHEN
        val testSubscriber = viewModel.articles.test(lifecycleOwner)
        useCaseProcessor.onNext(testArticles)

        //THEN
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertValue { results ->
            numberOfArticlesForTestChannel == results.count() && results.isEmpty()
        }
    }

    @Test
    fun `GIVEN (a sorting option) WHEN (sortFeedBy is called) THEN(the list of articles is correctly ordered)`() {

        val testArticlesOrderedByPublicationDate = ArticleModelMapper.map(testArticlesOrdered)
        val testArticlesOrderedByChannel = ArticleModelMapper.map(testArticlesOrdered)

        testSortingOption(FeedSortOptions.BY_PUBLICATION_DATE, testArticlesOrderedByPublicationDate) { it.publicationDate }
        testSortingOption(FeedSortOptions.BY_CHANNEL, testArticlesOrderedByChannel) { it.channelName }
    }

    private fun testSortingOption(
        options: FeedSortOptions,
        testArticlesOrdered: List<Article>,
        mapper: (Article) -> String
    ) {
        //GIVEN
        val useCaseProcessor = PublishProcessor.create<List<Article>>()
        val testArticlesShuffled = testArticlesOrdered.shuffled()
        every { getAllArticlesUseCase.execute(any()) } returns useCaseProcessor
        every { appPreferencesMock.sortingOptionSelected } returns options
        every { appPreferencesMock.channelFilterSelected } returns ""

        //WHEN
        val testSubscriber = viewModel.articles.test(lifecycleOwner)
        useCaseProcessor.onNext(testArticlesShuffled)

        //THEN
        testSubscriber.assertNoErrors()
        testSubscriber.assertValueCount(1)
        testSubscriber.assertValue { results ->
            Assert.assertEquals(testArticlesOrdered.map(mapper), results.map(mapper))
            results.count() == testArticlesShuffled.count()
        }
    }
}

class TestLifecycleOwner : LifecycleOwner {

    private val registry = LifecycleRegistry(this).apply {
        currentState = Lifecycle.State.RESUMED
    }

    override val lifecycle: Lifecycle
        get() = registry

    fun onResume() {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
    }

    fun onDestroy() {
        registry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    }
}

fun <T> LiveData<T>.test(lifecycle: LifecycleOwner): TestSubscriber<T> {
    val processor = PublishProcessor.create<T>()
    val testSubscriber = processor.test()
    observe(lifecycle) {
        processor.onNext(it)
    }
    return testSubscriber
}