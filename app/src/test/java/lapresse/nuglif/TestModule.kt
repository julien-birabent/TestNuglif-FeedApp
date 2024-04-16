package lapresse.nuglif

import lapresse.nuglif.data.ArticleRepository
import lapresse.nuglif.data.model.ArticleModel
import lapresse.nuglif.data.source.DataSource
import lapresse.nuglif.domain.GetAllArticlesUseCase
import lapresse.nuglif.thread.SchedulerProvider
import org.koin.dsl.module

val testModule = module {
    single<SchedulerProvider> { TestSchedulerProvider }
    single<DataSource<ArticleModel>> { ArticleTestDataSource }
    single { ArticleRepository(get()) }
    single { GetAllArticlesUseCase() }
}