package lapresse.nuglif.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import lapresse.nuglif.domain.GetAllArticlesUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ArticleFeedViewModel : ViewModel(), KoinComponent {

    private val allArticlesUseCase : GetAllArticlesUseCase by inject()

    val allArticles = allArticlesUseCase.build(Unit).toLiveData()
}