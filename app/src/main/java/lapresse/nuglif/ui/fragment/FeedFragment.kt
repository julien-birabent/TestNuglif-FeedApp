package lapresse.nuglif.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import lapresse.nuglif.R
import lapresse.nuglif.ui.ArticleFeedViewModel
import lapresse.nuglif.ui.LayoutManagerFactory
import lapresse.nuglif.ui.adapter.ArticleAdapter
import lapresse.nuglif.ui.item.ArticleFeedListItem
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private val viewModel: ArticleFeedViewModel by sharedViewModel<ArticleFeedViewModel>()
    private lateinit var adapter: ArticleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArticleAdapter(mutableListOf())
        initRecyclerView(adapter)

        viewModel.allArticles.observe(viewLifecycleOwner) { newArticles ->
            adapter.updateList(newArticles)
        }
    }

    private fun initRecyclerView(adapter: ArticleAdapter) {
        view?.findViewById<RecyclerView>(R.id.articleFeedRecyclerView)?.apply {
            setHasFixedSize(false)
            layoutManager = LayoutManagerFactory.createLinearLayoutManager(this, RecyclerView.VERTICAL)
            itemAnimator = DefaultItemAnimator()
            this.adapter = adapter
        }
    }
}