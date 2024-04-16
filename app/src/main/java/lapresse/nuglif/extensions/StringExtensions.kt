package lapresse.nuglif.extensions

import lapresse.nuglif.app.NuglifApplication
import java.text.DateFormat

fun String.toDisplayFormat(): String? {
    return NuglifApplication.appDateFormat.parse(this)?.let { date ->
        DateFormat.getDateInstance(DateFormat.FULL).format(date)
    }
}