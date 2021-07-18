package com.alaaeddinalbarghoth.mygallery.presentation.activities.main.feed

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.alaaeddinalbarghoth.mygallery.R
import com.alaaeddinalbarghoth.mygallery.databinding.FragmentFeedBinding
import com.alaaeddinalbarghoth.mygallery.presentation.activities.camera.CameraActivity
import com.alaaeddinalbarghoth.mygallery.presentation.activities.main.feed.adapter.FeedsAdapter
import com.alaaeddinalbarghoth.mygallery.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FeedFragment : Fragment() {

    var isGrid = true

    @Inject
    lateinit var feedsAdapter: FeedsAdapter
    private lateinit var binding: FragmentFeedBinding

    private val viewModel: FeedViewModel by viewModels()

    // region LifeCycle
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_feed,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        setupListeners()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getFeedsList()
    }
    // endregion

    private fun initAdapter() {
        binding.rvFeeds.layoutManager =
            GridLayoutManager(requireContext(), NUM_OF_COLUMNS)
        binding.rvFeeds.adapter = feedsAdapter
    }

    private fun setupListeners() {
        binding.btnAddImage.setOnClickListener {
            requireContext().startActivity(Intent(requireActivity(), CameraActivity::class.java))
        }

        binding.includedLayout.ivCamera.setOnClickListener {
            startActivity(Intent(requireActivity(), CameraActivity::class.java))
        }

        binding.includedLayout.ivSplit.setOnClickListener {
            if (isGrid) {
                isGrid = false
                binding.rvFeeds.layoutManager =
                    StaggeredGridLayoutManager(NUM_OF_COLUMNS, LinearLayoutManager.VERTICAL)
            } else {
                isGrid = true
                binding.rvFeeds.layoutManager =
                    GridLayoutManager(requireContext(), NUM_OF_COLUMNS)
            }
        }

        binding.includedLayout.ivSearch.setOnClickListener {
            with(binding.includedLayout.etSearch.text) {
                if (!this.isNullOrEmpty()) {
                    hideSearchIcon()
                    viewModel.search(this.toString())
                } else
                    viewModel.getFeedsList()
            }
        }
    }

    private fun observeViewModel() {
        viewModel.feedsList.observe(viewLifecycleOwner, {
            showSearchIcon()
            feedsAdapter.submitList(null)

            if (it.isEmpty()) {
                requireContext().toast("No Results :(")
                return@observe
            }

            showFeedsList()
            feedsAdapter.submitList(it)
        })
    }

    private fun showFeedsList() {
        binding.layoutFeeds.visibility = View.VISIBLE
        binding.layoutPlaceholder.visibility = View.GONE
    }

    private fun showSearchIcon() {
        binding.includedLayout.ivSearch.visibility = View.VISIBLE
        binding.includedLayout.progressBarLoading.visibility = View.GONE
    }

    private fun hideSearchIcon() {
        binding.includedLayout.ivSearch.visibility = View.GONE
        binding.includedLayout.progressBarLoading.visibility = View.VISIBLE
    }

    companion object {
        const val NUM_OF_COLUMNS = 2
    }
}