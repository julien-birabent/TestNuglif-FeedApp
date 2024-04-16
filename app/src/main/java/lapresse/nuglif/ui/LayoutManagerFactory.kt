package lapresse.nuglif.ui

import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lapresse.nuglif.R

class LayoutManagerFactory {

    companion object {
        fun createLinearLayoutManager(
            recyclerView: RecyclerView,
            orientation: Int,
            @DrawableRes decorationDrawable: Int
        ): LinearLayoutManager {
            return LinearLayoutManager(recyclerView.context, orientation, false).apply {
                applyDecorations(recyclerView,orientation, decorationDrawable)
            }
        }

        private fun applyDecorations(recyclerView: RecyclerView, orientation: Int, @DrawableRes decorationDrawable: Int){
            val dividerItemDecoration = DividerItemDecoration(
                recyclerView.context,
                orientation
            ).apply {
                recyclerView.context.getDrawable(decorationDrawable)?.let {
                        setDrawable(it)
                    }
            }
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }
}