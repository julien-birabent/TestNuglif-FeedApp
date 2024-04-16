package lapresse.nuglif.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import lapresse.nuglif.R
import lapresse.nuglif.ui.item.ArticleFeedListItem
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale


class ArticleViewHolder(view: View) : ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.articleItemImage)
    val channelName: TextView = view.findViewById(R.id.articleItemChannelName)
    val title: TextView = view.findViewById(R.id.articleItemTitle)
    val publicationDate: TextView = view.findViewById(R.id.articleItemPublicationDate)
}

class ArticleAdapter(itemList: MutableList<ArticleFeedListItem>) :
    SimpleDataAdapter<ArticleFeedListItem, ArticleViewHolder>(itemList, R.layout.feed_article_list_item) {

    private val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    override fun createViewHolder(view: View): ArticleViewHolder = ArticleViewHolder(view)

    override fun bindItemToViewHolder(item: ArticleFeedListItem, viewHolder: ArticleViewHolder) {
        viewHolder.apply {
            Glide.with(viewHolder.itemView.context).load(item.photoUrl).into(image)
            channelName.text = item.channelName
            title.text = item.title
            publicationDate.text = dateFormat.parse(item.publicationDate)?.let { date ->
                DateFormat.getDateInstance(DateFormat.FULL).format(date)
            }
        }
    }
}