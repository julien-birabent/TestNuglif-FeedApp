package lapresse.nuglif

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListAdapter(private val list: List<ArticleDO>, private val context: Context) : BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val textView: TextView = if (convertView == null) {
            LayoutInflater.from(context).inflate(R.layout.list_item, parent, false) as TextView
        } else {
            convertView as TextView
        }

        val (_, channelName, title) = list[position]
        textView.text = "[$channelName] $title"

        return textView
    }
}
