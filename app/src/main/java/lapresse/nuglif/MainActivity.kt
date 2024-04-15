package lapresse.nuglif

import android.os.Bundle
import android.view.Menu
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var confusingRandomLogger: ConfusingRandomLogger

    private var listView: ListView? = null
    private val articleList: List<ArticleDO>
        get() {
            try {
                val articles = resources.openRawResource(R.raw.articles)
                val reader = BufferedReader(InputStreamReader(articles, "UTF-8"))
                val listType = object : TypeToken<ArrayList<ArticleDO>>() {}.type
                return Gson().fromJson(reader, listType)
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.list)
        val list = articleList
        listView?.adapter = ListAdapter(list, this)

        confusingRandomLogger = ConfusingRandomLogger()
        confusingRandomLogger.startLogging()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onDestroy() {
        confusingRandomLogger.stopLogging()
        super.onDestroy()
    }
}
