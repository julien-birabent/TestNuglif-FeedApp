package lapresse.nuglif.app.preferences

import android.content.SharedPreferences
import lapresse.nuglif.extensions.enum
import lapresse.nuglif.extensions.string
import lapresse.nuglif.ui.FeedSortOptions

class AppPreferences(sharedPreferences: SharedPreferences) {

    var channelFilterSelected: String by sharedPreferences.string("channelFilterSelected", "")
    var sortingOptionSelected: FeedSortOptions by sharedPreferences.enum(
        "sortingOptionSelected",
        FeedSortOptions.BY_PUBLICATION_DATE,
        FeedSortOptions::class.java
    )
}
