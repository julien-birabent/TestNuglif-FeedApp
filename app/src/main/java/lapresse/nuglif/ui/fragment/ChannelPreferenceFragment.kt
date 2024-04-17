package lapresse.nuglif.ui.fragment

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import lapresse.nuglif.R
import lapresse.nuglif.extensions.actionBarTitle
import lapresse.nuglif.extensions.displayNavigateUpButton
import lapresse.nuglif.extensions.initRecyclerView
import lapresse.nuglif.ui.viewmodel.ArticleFeedViewModel
import lapresse.nuglif.ui.adapter.channel.ChannelPreferenceAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class ChannelPreferenceFragment : Fragment(R.layout.fragment_filter_preference) {

    private val viewModel: ArticleFeedViewModel by sharedViewModel<ArticleFeedViewModel>()
    private lateinit var adapter: ChannelPreferenceAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        displayNavigateUpButton()
        actionBarTitle(getString(R.string.fragment_channel_preference))

        handleBackPressed()
        handleHomeButtonClicked()

        adapter = ChannelPreferenceAdapter(mutableListOf(), ::onChannelItemClicked)
        view.findViewById<RecyclerView>(R.id.channelPreferenceRecyclerview)
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

    private fun closePage(){
        findNavController().popBackStack()
    }

    private fun onChannelItemClicked(channel: String) {
        viewModel.filterSelectionByChannelName(channel)
        closePage()
    }

    private fun handleHomeButtonClicked(){
        view?.findViewById<View>(R.id.channelPreferenceHomeButton)?.setOnClickListener {
            viewModel.filterSelectionByChannelName("")
            closePage()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}