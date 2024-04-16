package lapresse.nuglif.app.preferences.delegate

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class EnumPreferences<T : Enum<T>>(
    private val sharedPreferences: SharedPreferences,
    private val key: String,
    private val enumClass: Class<T>,
    private val defaultValue: T
) : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return sharedPreferences.getString(key, defaultValue.name)?.let { valueOf(enumClass, it) } ?: defaultValue
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        sharedPreferences.edit()
            .putString(key, value.name)
            .apply()
    }

    private fun < T : Enum<T>> valueOf(enumClass: Class<T>, type: String): T? {
        return try {
            java.lang.Enum.valueOf(enumClass, type)
        } catch (e: IllegalArgumentException) {
            null
        }
    }
}