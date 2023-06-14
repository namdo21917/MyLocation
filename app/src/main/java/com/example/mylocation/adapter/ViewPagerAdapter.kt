package com.example.mylocation.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mylocation.ui.favorites.FavoriteFragment
import com.example.mylocation.ui.maps.MapFragment
import com.example.mylocation.ui.shared.SharedFragment

class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    private val fragmentList: MutableList<Pair<String, Fragment>>
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position].second
    }

    fun getName(position: Int): String {
        return fragmentList[position].first
    }
}