package lapresse.nuglif.ui.adapter.channel

import android.view.View
import android.widget.TextView
import com.google.android.material.card.MaterialCardView
import lapresse.nuglif.R
import lapresse.nuglif.extensions.randomColor
import lapresse.nuglif.ui.adapter.base.SimpleDataAdapter
import lapresse.nuglif.ui.adapter.base.ViewHolder

class ChannelPreferenceViewHolder(view: View) : ViewHolder(view) {
    val coloredCard: MaterialCardView = view.findViewById(R.id.filterPreferenceItemColoredCard)
    val channelName: TextView = view.findViewById(R.id.filterPreferenceItemText)
}

class ChannelPreferenceAdapter (itemList: MutableList<String>, private val onItemClicked : (String) -> Unit) :
    SimpleDataAdapter<String, ChannelPreferenceViewHolder>(itemList, R.layout.filter_preference_list_item)  {

    override fun bindItemToViewHolder(item: String, viewHolder: ChannelPreferenceViewHolder) {
        viewHolder.apply {
            coloredCard.setCardBackgroundColor(Int.randomColor())
            channelName.text = item
            viewHolder.view.setOnClickListener { onItemClicked(item) }
        }
    }

    override fun createViewHolder(view: View): ChannelPreferenceViewHolder = ChannelPreferenceViewHolder(view)
}