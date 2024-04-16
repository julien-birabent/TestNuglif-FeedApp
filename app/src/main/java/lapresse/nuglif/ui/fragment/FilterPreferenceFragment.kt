package lapresse.nuglif.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import lapresse.nuglif.R
import lapresse.nuglif.extensions.hideActionBar
import lapresse.nuglif.extensions.initRecyclerView
import lapresse.nuglif.ui.ArticleFeedViewModel
import lapresse.nuglif.ui.adapter.channel.ChannelPreferenceAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class FilterPreferenceFragment : Fragment(R.layout.fragment_filter_preference) {

    private val viewModel: ArticleFeedViewModel by sharedViewModel<ArticleFeedViewModel>()
    private lateinit var adapter: ChannelPreferenceAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideActionBar()
        handleBackPressed()
        handleCloseButtonClicked()
        handleHomeButtonClicked()

        adapter = ChannelPreferenceAdapter(mutableListOf(), ::onChannelItemClicked)
        view.findViewById<RecyclerView>(R.id.filterPreferenceRecyclerview)
            .initRecyclerView(adapter, R.drawable.item_decoration_divider_line)

        viewModel.allChannels.observe(viewLifecycleOwner) { channels ->
            adapter.updateList(channels)
        }

    }

    private fun handleBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback {
            closePage()
        }
    }

    private fun handleCloseButtonClicked() {
        view?.findViewById<ImageView>(R.id.filterPreferenceCloseButton)?.setOnClickListener {
            closePage()
        }
    }

    private fun closePage(){
        findNavController().popBackStack()
    }

    private fun onChannelItemClicked(channel: String) {
        viewModel.filterSelectionByChannelName(channel)
        closePage()
    }

    private fun handleHomeButtonClicked(){
        view?.findViewById<View>(R.id.filterPreferenceHome)?.setOnClickListener {
            viewModel.filterSelectionByChannelName("")
            closePage()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}