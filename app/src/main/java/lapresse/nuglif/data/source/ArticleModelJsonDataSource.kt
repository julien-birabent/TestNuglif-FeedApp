package lapresse.nuglif.data.source

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.rxjava3.core.Flowable
import lapresse.nuglif.R
import lapresse.nuglif.data.model.ArticleModel
import java.io.BufferedReader
import java.io.InputStreamReader

class ArticleModelJsonDataSource(private val context: Context) : DataSource<ArticleModel> {

    private val articles by lazy { parseJson() }

    private fun parseJson() : List<ArticleModel> {
        try {
            val articles = context.resources.openRawResource(R.raw.articles)
            val reader = BufferedReader(InputStreamReader(articles, "UTF-8"))
            val listType = object : TypeToken<ArrayList<ArticleModel>>() {}.type
            return Gson().fromJson(reader, listType)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    override fun fetchData(): Flowable<List<ArticleModel>> = Flowable.just(articles)
}