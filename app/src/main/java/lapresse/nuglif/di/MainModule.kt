package lapresse.nuglif.di

import lapresse.nuglif.data.model.ArticleModel
import lapresse.nuglif.data.source.ArticleModelJsonDataSource
import lapresse.nuglif.data.ArticleRepository
import lapresse.nuglif.data.source.DataSource
import lapresse.nuglif.domain.GetArticleByChannelUseCase
import lapresse.nuglif.domain.GetAllArticlesUseCase
import lapresse.nuglif.thread.AppSchedulerProvider
import lapresse.nuglif.thread.SchedulerProvider
import lapresse.nuglif.ui.ArticleFeedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single<SchedulerProvider> { AppSchedulerProvider() }

    single<DataSource<ArticleModel>> { ArticleModelJsonDataSource(androidContext()) }
    single { ArticleRepository(get()) }
    single { ArticleFeedViewModel() }

    single { GetArticleByChannelUseCase() }
    single { GetAllArticlesUseCase() }

    viewModel { ArticleFeedViewModel() }
}