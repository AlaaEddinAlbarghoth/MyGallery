package com.alaaeddinalbarghoth.mygallery.presentation.activities.main.container

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.alaaeddinalbarghoth.mygallery.R
import com.alaaeddinalbarghoth.mygallery.databinding.FragmentContainerBinding
import com.alaaeddinalbarghoth.mygallery.presentation.activities.main.adapter.MainPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class ContainerFragment : Fragment() {

    private lateinit var binding: FragmentContainerBinding
    private lateinit var mainPagerAdapter: MainPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_container,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    private fun init() {
        binding.pager.isUserInputEnabled = false
        mainPagerAdapter = MainPagerAdapter(this)
        binding.pager.adapter = mainPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            if (position == 0)
                tab.text = getString(R.string.feeds)
            else
                tab.text = getString(R.string.trash)

        }.attach()
    }
}