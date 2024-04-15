package lapresse.nuglif.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import lapresse.nuglif.R
import lapresse.nuglif.ui.ArticleFeedViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class FeedFragment : Fragment(R.layout.fragment_feed) {

    private val viewModel: ArticleFeedViewModel by sharedViewModel<ArticleFeedViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.allArticles.observe(viewLifecycleOwner) {
            Log.e("test", it.toString())
        }
    }
}