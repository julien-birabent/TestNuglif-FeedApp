package lapresse.nuglif.extensions

import android.content.SharedPreferences
import lapresse.nuglif.app.preferences.delegate.EnumPreferences
import lapresse.nuglif.app.preferences.delegate.StringPreference
import kotlin.properties.ReadWriteProperty

fun SharedPreferences.string(
    key: String,
    defaultValue: String = ""
): ReadWriteProperty<Any, String> =
    StringPreference(this, key, defaultValue)

fun <T: Enum<T>>SharedPreferences.enum(
    key: String,
    defaultValue: T,
    enumClass: Class<T>
): ReadWriteProperty<Any, T> =
    EnumPreferences(this, key, enumClass ,defaultValue)
