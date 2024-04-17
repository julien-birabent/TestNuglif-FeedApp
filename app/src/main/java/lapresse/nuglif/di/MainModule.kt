package lapresse.nuglif.di

import android.content.Context
import android.content.SharedPreferences
import lapresse.nuglif.app.preferences.AppPreferences
import lapresse.nuglif.data.model.ArticleDTO
import lapresse.nuglif.data.source.ArticleModelJsonDataSource
import lapresse.nuglif.data.ArticleRepository
import lapresse.nuglif.data.source.DataSource
import lapresse.nuglif.domain.GetArticleByChannelUseCase
import lapresse.nuglif.domain.GetAllArticlesUseCase
import lapresse.nuglif.thread.AppSchedulerProvider
import lapresse.nuglif.thread.SchedulerProvider
import lapresse.nuglif.ui.viewmodel.ArticleFeedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single<SchedulerProvider> { AppSchedulerProvider() }
    single<SharedPreferences> { androidContext().getSharedPreferences("private_shared_preferences", Context.MODE_PRIVATE)}
    single { AppPreferences(get()) }

    single<DataSource<ArticleDTO>> { ArticleModelJsonDataSource(androidContext()) }
    single { ArticleRepository(get()) }

    single { GetArticleByChannelUseCase() }
    single { GetAllArticlesUseCase() }

    viewModel { ArticleFeedViewModel(get()) }
}