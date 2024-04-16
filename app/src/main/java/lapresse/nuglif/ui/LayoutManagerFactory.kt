package lapresse.nuglif.ui

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lapresse.nuglif.R

class LayoutManagerFactory {

    companion object {
        fun createLinearLayoutManager(
            recyclerView: RecyclerView,
            orientation: Int
        ): LinearLayoutManager {
            return LinearLayoutManager(recyclerView.context, orientation, false).apply {
                applyDecorations(recyclerView,orientation)
            }
        }

        private fun applyDecorations(recyclerView: RecyclerView, orientation: Int){
            val dividerItemDecoration = DividerItemDecoration(
                recyclerView.context,
                orientation
            ).apply {
                recyclerView.context.getDrawable(R.drawable.item_decoration_divider_transparent)
                    ?.let {
                        setDrawable(it)
                    }
            }
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }
}