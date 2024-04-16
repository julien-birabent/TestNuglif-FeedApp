package lapresse.nuglif.ui.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import lapresse.nuglif.R
import lapresse.nuglif.ui.item.ArticleFeedListItem
import java.text.SimpleDateFormat

class ArticleViewHolder(view: View) : ViewHolder(view) {
    val image = view.findViewById<ImageView>(R.id.articleItemImage)
    val channelName = view.findViewById<TextView>(R.id.articleItemChannelName)
    val title = view.findViewById<TextView>(R.id.articleItemTitle)
    val publicationDate = view.findViewById<TextView>(R.id.articleItemPublicationDate)
}

class ArticleAdapter(itemList: MutableList<ArticleFeedListItem>) :
    SimpleDataAdapter<ArticleFeedListItem, ArticleViewHolder>(itemList, R.layout.feed_article_list_item) {

    private var dateFormat: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy")

    override fun createViewHolder(view: View): ArticleViewHolder = ArticleViewHolder(view)

    override fun bindItemToViewHolder(item: ArticleFeedListItem, viewHolder: ArticleViewHolder) {
        viewHolder.apply {
            Glide.with(image).load(item.photoUrl).into(image)
            channelName.text = item.channelName
            title.text = item.title
            publicationDate.text = dateFormat.parse(item.publicationDate)?.toString() ?: ""
        }
    }
}