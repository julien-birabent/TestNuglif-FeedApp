package lapresse.nuglif.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import lapresse.nuglif.R
import lapresse.nuglif.extensions.initRecyclerView
import lapresse.nuglif.extensions.showActionBar
import lapresse.nuglif.ui.ArticleFeedViewModel
import lapresse.nuglif.ui.FeedSortOptions
import lapresse.nuglif.ui.LayoutManagerFactory
import lapresse.nuglif.ui.adapter.article.ArticleAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private val viewModel: ArticleFeedViewModel by sharedViewModel<ArticleFeedViewModel>()
    private lateinit var adapter: ArticleAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        showActionBar()

        adapter = ArticleAdapter(mutableListOf())
        view.findViewById<RecyclerView>(R.id.articleFeedRecyclerView).initRecyclerView(adapter)

        viewModel.articles.observe(viewLifecycleOwner) { newArticles ->
            adapter.updateList(newArticles)
        }
    }

    private fun showSortingMenu(anchor: View, onOptionClicked: (Int) -> Unit) {
        val popupMenu = PopupMenu(requireContext(), anchor)

        popupMenu.menuInflater.inflate(R.menu.menu_feed_sort, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            onOptionClicked(menuItem.itemId)
            true
        }
        popupMenu.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                val menuItemView  = requireActivity().findViewById<View>(item.itemId)
                showSortingMenu(menuItemView) { optionClicked ->
                    viewModel.sortFeedBy(mapMenuIdToOption(optionClicked))
                }
                true
            }
            R.id.action_filter_by_channel -> {
                findNavController().navigate(R.id.action_feedFragment_to_filterPreferenceFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun mapMenuIdToOption(menuItemId: Int) : FeedSortOptions {
        return when(menuItemId){
            R.id.sort_by_channel -> FeedSortOptions.BY_CHANNEL
            else -> FeedSortOptions.BY_PUBLICATION_DATE
        }
    }
}