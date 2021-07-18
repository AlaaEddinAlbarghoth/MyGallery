package com.alaaeddinalbarghoth.mygallery.presentation.activities.main.trash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alaaeddinalbarghoth.mygallery.R
import com.alaaeddinalbarghoth.mygallery.data.model.FeedItem
import com.alaaeddinalbarghoth.mygallery.databinding.FragmentTrashBinding
import com.alaaeddinalbarghoth.mygallery.presentation.activities.main.trash.adapter.TrashAdapter
import com.alaaeddinalbarghoth.mygallery.utils.SwipeToDeleteCallback
import com.alaaeddinalbarghoth.mygallery.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrashFragment : Fragment() {

    private lateinit var dataList: ArrayList<FeedItem>

    @Inject
    lateinit var trashAdapter: TrashAdapter
    private lateinit var binding: FragmentTrashBinding
    private val viewModel: TrashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_trash,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        observeViewModel()
    }

    override fun onResume() {
        super.onResume()

        viewModel.getDeletedFeeds()
    }

    private fun initAdapter() {
        binding.rvFeeds.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFeeds.adapter = trashAdapter

        ItemTouchHelper(object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(
                @NonNull viewHolder: RecyclerView.ViewHolder,
                i: Int
            ) {
                trashAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                viewModel.remove(trashAdapter.currentList[viewHolder.adapterPosition].id)
            }
        }).attachToRecyclerView(binding.rvFeeds)
    }

    private fun observeViewModel() {
        viewModel.feedsList.observe(viewLifecycleOwner, {
            dataList = it as ArrayList<FeedItem>

            if (it.isEmpty()) {
                requireContext().toast("No Results :(")
                showPlaceHolder()
                return@observe
            }
            hidePlaceHolder()
            trashAdapter.submitList(it)
        })
    }

    private fun showPlaceHolder() {
        binding.rvFeeds.visibility = View.GONE
        binding.layoutPlaceholder.visibility = View.VISIBLE
    }

    private fun hidePlaceHolder() {
        binding.rvFeeds.visibility = View.VISIBLE
        binding.layoutPlaceholder.visibility = View.GONE
    }
}