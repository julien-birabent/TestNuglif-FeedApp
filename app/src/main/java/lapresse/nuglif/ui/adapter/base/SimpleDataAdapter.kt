package lapresse.nuglif.ui.adapter.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleDataAdapter <T, VH: ViewHolder> (private val itemList : MutableList<T>,
                                                      @LayoutRes private val layoutResId: Int): RecyclerView.Adapter<VH>(){

    fun updateList(newList: List<T>) {
        val diffUtil = getDiffUtilCallback(itemList, newList)
        val result = DiffUtil.calculateDiff(diffUtil)
        itemList.clear()
        itemList.addAll(newList)
        result.dispatchUpdatesTo(this)
    }

    private fun getDiffUtilCallback(
        oldList: List<T>,
        newList: List<T>
    ): BaseDiffCallback<T> {
        return BaseDiffCallback(oldList, newList, areItemsTheSame = { oldItem, newItem ->
            oldItem == newItem
        }, areContentTheSame = { oldItem, newItem ->
            oldItem == newItem
        })
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        return createViewHolder(view)
    }

    override fun getItemCount(): Int = itemList.count()

    override fun onBindViewHolder(holder: VH, position: Int) {
        bindItemToViewHolder(itemList[position], holder)
    }

    protected abstract fun bindItemToViewHolder(item : T, viewHolder: VH)

    protected abstract fun createViewHolder(view: View) : VH
}

open class ViewHolder(val view: View) : RecyclerView.ViewHolder(view)