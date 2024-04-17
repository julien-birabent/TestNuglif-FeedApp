package lapresse.nuglif.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import lapresse.nuglif.R
import lapresse.nuglif.app.ResultState
import lapresse.nuglif.extensions.actionBarTitle
import lapresse.nuglif.extensions.displayNavigateUpButton
import lapresse.nuglif.extensions.toDisplayFormat
import lapresse.nuglif.ui.model.Article
import lapresse.nuglif.ui.viewmodel.ArticleFeedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ArticleDetailsFragment : Fragment(R.layout.fragment_article_details) {

    companion object {
        const val ARG_ARTICLE_ID = "articleId"
    }

    private val viewModel: ArticleFeedViewModel by sharedViewModel<ArticleFeedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        handleBackPressed()
        displayNavigateUpButton()
        actionBarTitle(getString(R.string.fragment_article_details))

        arguments?.getString(ARG_ARTICLE_ID)?.let {
            viewModel.getArticleById(it).observe(viewLifecycleOwner) { results ->
                when (results) {
                    is ResultState.Success -> displayArticleDetails(results.resultData)
                    else -> findNavController().popBackStack()
                }
            }
        }
    }

    private fun displayArticleDetails(article: Article) {
        requireView().run {
            Glide.with(requireContext()).load(article.photoUrl)
                .into(findViewById(R.id.articleDetailsImage))
            findViewById<TextView>(R.id.articleDetailsChannelName).text = article.channelName
            findViewById<TextView>(R.id.articleDetailsTitle).text = article.title
            findViewById<TextView>(R.id.articleDetailsLead).text = article.lead
            findViewById<TextView>(R.id.articleDetailsPublicationDate).text =
                article.publicationDate.toDisplayFormat()
        }
    }

    private fun handleBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}