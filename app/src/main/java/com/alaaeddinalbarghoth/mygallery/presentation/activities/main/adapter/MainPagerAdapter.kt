package com.alaaeddinalbarghoth.mygallery.presentation.activities.main.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alaaeddinalbarghoth.mygallery.presentation.activities.main.feed.FeedFragment
import com.alaaeddinalbarghoth.mygallery.presentation.activities.main.trash.TrashFragment

class MainPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
       return when (position) {
            0 -> FeedFragment()
           else -> TrashFragment()
        }
    }
}
