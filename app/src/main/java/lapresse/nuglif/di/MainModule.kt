package lapresse.nuglif.di

import lapresse.nuglif.data.model.ArticleModel
import lapresse.nuglif.data.source.ArticleModelJsonDataSource
import lapresse.nuglif.data.ArticleRepository
import lapresse.nuglif.data.source.DataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {

    single<DataSource<ArticleModel>> { ArticleModelJsonDataSource(androidContext()) }
    single { ArticleRepository(get()) }
}