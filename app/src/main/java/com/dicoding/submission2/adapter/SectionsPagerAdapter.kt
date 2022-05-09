package com.dicoding.submission2.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.submission2.fragment.FollowerFragment
import com.dicoding.submission2.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, fragmentManager: FragmentManager, data: Bundle) : FragmentStateAdapter(activity) {

    private var fragmentBundle: Bundle = data
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowerFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}