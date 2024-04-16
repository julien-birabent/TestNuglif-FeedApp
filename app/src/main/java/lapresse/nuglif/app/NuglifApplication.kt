package lapresse.nuglif.app

import android.app.Application
import lapresse.nuglif.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.text.SimpleDateFormat
import java.util.Locale

class NuglifApplication : Application() {

    companion object {
        val appDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NuglifApplication)
            modules(mainModule)
        }
    }
}