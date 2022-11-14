package com.heyproject.githubapps.presentation.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.heyproject.githubapps.presentation.detail.FollowersFragment

/**
Written by Yayan Rahmat Wijaya on 11/14/2022 08:35
Github : https://github.com/yayanrw
 **/

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowersFragment()
        }
        return fragment as Fragment
    }
}