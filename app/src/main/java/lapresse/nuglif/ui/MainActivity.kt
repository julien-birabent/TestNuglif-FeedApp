package lapresse.nuglif.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import lapresse.nuglif.ConfusingRandomLogger
import lapresse.nuglif.R

class MainActivity : AppCompatActivity() {

    private lateinit var confusingRandomLogger: ConfusingRandomLogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
