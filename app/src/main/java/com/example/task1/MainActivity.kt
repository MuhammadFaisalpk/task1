package com.example.task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.task1.containerViews.RootTab1Fragment
import com.example.task1.containerViews.RootTab2Fragment
import com.example.task1.containerViews.RootTab3Fragment
import com.example.task1.databinding.ActivityMainBinding
import com.example.task1.view.ImagesFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        setStatePageAdapter()
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                val fm = supportFragmentManager
                val ft = fm.beginTransaction()
                val count = fm.backStackEntryCount
                if (count >= 1) {
                    supportFragmentManager.popBackStack()
                }
                ft.commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                // setAdapter();


            }

            override fun onTabReselected(tab: TabLayout.Tab) {

                //   viewPager.notifyAll();
            }
        })
    }

    private fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewPager = binding.pager
        tabLayout = binding.tabs
    }

    private fun setStatePageAdapter() {
        val myViewPageStateAdapter: MyViewPageStateAdapter =
            MyViewPageStateAdapter(supportFragmentManager)
        myViewPageStateAdapter.addFragment(ImagesFragment(), "Images")
        myViewPageStateAdapter.addFragment(RootTab2Fragment(), "Videos")
        myViewPageStateAdapter.addFragment(RootTab3Fragment(), "Docs")
        viewPager.adapter = myViewPageStateAdapter
        tabLayout.setupWithViewPager(viewPager, true)

    }

    class MyViewPageStateAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        private val fragmentList: MutableList<Fragment> = ArrayList<Fragment>()
        private val fragmentTitleList: MutableList<String> = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitleList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitleList.add(title)

        }
    }
}