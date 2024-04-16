package lapresse.nuglif.extensions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.displayNavigateUpButton() {
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
}

fun Fragment.hideNavigateUpButton() {
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
}

fun Fragment.actionBarTitle(title: String) {
    (activity as AppCompatActivity).supportActionBar?.title = title
}

fun Fragment.showActionBar() {
    (activity as AppCompatActivity).supportActionBar?.show()
}
