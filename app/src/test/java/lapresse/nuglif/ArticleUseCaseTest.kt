package lapresse.nuglif

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import lapresse.nuglif.domain.ArticleModelMapper
import lapresse.nuglif.domain.GetAllArticlesUseCase
import lapresse.nuglif.domain.GetArticleByChannelUseCase
import lapresse.nuglif.domain.SortArticlesUseCase
import lapresse.nuglif.ui.FeedSortOptions
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.test.KoinTest
import kotlin.test.assertTrue

class ArticleUseCaseTest : KoinTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        startKoin {
            modules(testModule)
        }
    }

    @Test
    fun `GIVEN (GetAllArticlesUseCase) WHEN(it is executed) THEN (All articles in the repository are returned in the proper data model)`() {
        //GIVEN
        val useCase = GetAllArticlesUseCase()
        val testArticles = ArticleTestDataSource.testArticlesOrdered

        //THEN
        val testSubscriber = useCase.execute(Unit).test()

        //THEN
        testSubscriber.assertValueCount(1)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValue {
            it.count() == testArticles.count()
        }
    }

    @Test
    fun `GIVEN (GetArticleByChannelUseCase and an existing channel name) WHEN(it is executed) THEN (only the articles matching that channel are returned)`() {
        //GIVEN
        val useCase = GetArticleByChannelUseCase()
        val channelTest = ArticleTestDataSource.testArticlesOrdered.first().channelName
        val testArticles = ArticleTestDataSource.testArticlesOrdered

        //THEN
        val testSubscriber = useCase.execute(channelTest).test()

        //THEN
        testSubscriber.assertValueCount(1)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValue {articles ->
            articles.all { it.channelName == channelTest }
        }
    }

    @Test
    fun `GIVEN (GetArticleByChannelUseCase and an empty channel name) WHEN(it is executed) THEN (every articles is returned)`() {
        //GIVEN
        val useCase = GetArticleByChannelUseCase()
        val channelTest = ""
        val testArticles = ArticleTestDataSource.testArticlesOrdered

        //THEN
        val testSubscriber = useCase.execute(channelTest).test()

        //THEN
        testSubscriber.assertValueCount(1)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValue {articles ->
            articles.count() == testArticles.count()
        }
    }

    @Test
    fun `GIVEN (GetArticleByChannelUseCase and a non existing channel name) WHEN(it is executed) THEN (an empty list is returned)`() {
        //GIVEN
        val useCase = GetArticleByChannelUseCase()
        val channelTest = "thischannelnamewillneverexistiswear"
        val testArticles = ArticleTestDataSource.testArticlesOrdered

        //THEN
        val testSubscriber = useCase.execute(channelTest).test()

        //THEN
        testSubscriber.assertValueCount(1)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValue { articles ->
            articles.isEmpty()
        }
    }

    @Test
    fun `GIVEN (a SortArticlesUseCase and a sorting option by publication date) WHEN(it is executed) THEN (the articles are sorted from most recent to less recent)`() {
        //GIVEN
        val useCase = SortArticlesUseCase()
        val testArticlesShuffled = ArticleModelMapper.map(ArticleTestDataSource.testArticlesOrdered).shuffled()
        val testArticlesOrdered = ArticleModelMapper.map(ArticleTestDataSource.testArticlesOrdered)
        val sortOption = FeedSortOptions.BY_PUBLICATION_DATE

        //THEN
        val testSubscriber = useCase.execute(testArticlesShuffled to sortOption).test()

        //THEN
        testSubscriber.assertValueCount(1)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValue { articles ->
            Assert.assertEquals(articles.map { it.publicationDate }, testArticlesOrdered.map { it.publicationDate })
            true
        }
    }

    @Test
    fun `GIVEN (a SortArticlesUseCase and a sorting option by channel name) WHEN(it is executed) THEN (the articles are sorted by channel name in natural order)`() {
        //GIVEN
        val useCase = SortArticlesUseCase()
        val testArticlesShuffled = ArticleModelMapper.map(ArticleTestDataSource.testArticlesOrdered).shuffled()
        val testArticlesOrdered = ArticleModelMapper.map(ArticleTestDataSource.testArticlesOrdered)
        val sortOption = FeedSortOptions.BY_CHANNEL

        //THEN
        val testSubscriber = useCase.execute(testArticlesShuffled to sortOption).test()

        //THEN
        testSubscriber.assertValueCount(1)
        testSubscriber.assertNoErrors()
        testSubscriber.assertValue { articles ->
            Assert.assertEquals(articles.map { it.channelName }, testArticlesOrdered.map { it.channelName })
            true
        }
    }

    @After
    fun after() {
        stopKoin()
    }

}