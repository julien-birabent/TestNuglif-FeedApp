package lapresse.nuglif.app

import android.app.Application
import lapresse.nuglif.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class NuglifApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@NuglifApplication)
            // Load modules
            modules(mainModule)
        }
    }
}