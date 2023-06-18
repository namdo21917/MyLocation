package com.example.mylocation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mylocation.adapter.ViewPagerAdapter
import com.example.mylocation.databinding.ActivityMainBinding
import com.example.mylocation.ui.favorites.FavoriteFragment
import com.example.mylocation.ui.maps.MapFragment
import com.example.mylocation.ui.shared.SharedFragment
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val viewPager2 = binding.pager
        viewPager2.isUserInputEnabled = false

        val tabLayout = binding.tabLayout

        val fragmentList = mutableListOf(
            Pair("Map", MapFragment()),
            Pair("Lịch sử", SharedFragment()),
            Pair("Yêu thích", FavoriteFragment())
        )
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, fragmentList)
        viewPager2.adapter = viewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = viewPagerAdapter.getName(position)
        }.attach()
    }
}