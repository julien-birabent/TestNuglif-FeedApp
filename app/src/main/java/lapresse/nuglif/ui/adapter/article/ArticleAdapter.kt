package lapresse.nuglif.ui.adapter.article

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import lapresse.nuglif.R
import lapresse.nuglif.extensions.toDisplayFormat
import lapresse.nuglif.ui.adapter.base.SimpleDataAdapter
import lapresse.nuglif.ui.adapter.base.ViewHolder
import lapresse.nuglif.ui.item.Article
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale


class ArticleViewHolder(view: View) : ViewHolder(view) {
    val image: ImageView = view.findViewById(R.id.articleItemImage)
    val channelName: TextView = view.findViewById(R.id.articleItemChannelName)
    val title: TextView = view.findViewById(R.id.articleItemTitle)
    val publicationDate: TextView = view.findViewById(R.id.articleItemPublicationDate)
}

class ArticleAdapter(itemList: MutableList<Article>, private val onArticleClicked : (String) -> Unit) :
    SimpleDataAdapter<Article, ArticleViewHolder>(itemList, R.layout.feed_article_list_item) {

    override fun createViewHolder(view: View): ArticleViewHolder = ArticleViewHolder(view)

    override fun bindItemToViewHolder(item: Article, viewHolder: ArticleViewHolder) {
        viewHolder.apply {
            Glide.with(viewHolder.itemView.context).load(item.photoUrl).into(image)
            channelName.text = item.channelName
            title.text = item.title
            publicationDate.text = item.publicationDate.toDisplayFormat()
            viewHolder.view.setOnClickListener { onArticleClicked(item.id) }
        }
    }
}