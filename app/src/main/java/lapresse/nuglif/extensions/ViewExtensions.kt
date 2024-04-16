package lapresse.nuglif.extensions

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import lapresse.nuglif.R
import lapresse.nuglif.ui.LayoutManagerFactory

fun RecyclerView.initRecyclerView(adapter: RecyclerView.Adapter<*>, @DrawableRes decoration : Int = R.drawable.item_decoration_divider_transparent) {
    setHasFixedSize(false)
    layoutManager = LayoutManagerFactory.createLinearLayoutManager(this, RecyclerView.VERTICAL, decoration)
    itemAnimator = DefaultItemAnimator()
    this.adapter = adapter
}