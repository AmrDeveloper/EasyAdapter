package com.amrdeveloper.easyadapterlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.amrdeveloper.easyadapterlibrary.data.FRAGMENTS_NUMBER
import com.amrdeveloper.easyadapterlibrary.ui.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewPager = findViewById<ViewPager2>(R.id.pager)
        val pagerAdapter = ScreenSlidePagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter
    }

    private inner class ScreenSlidePagerAdapter(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle
    ) : FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int = FRAGMENTS_NUMBER

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> ArrayAdapterFragment()
                1 -> RecyclerAdapterFragment()
                2 -> ListAdapterFragment()
                3 -> PagingAdapterFragment()
                else -> ExpandableAdapterFragment()
            }
        }
    }
}