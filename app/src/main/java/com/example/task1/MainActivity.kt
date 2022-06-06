package com.example.task1

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.task1.databinding.ActivityMainBinding
import com.example.task1.utils.Interfaces
import com.example.task1.view.DocsFragment
import com.example.task1.view.ImagesFragment
import com.example.task1.view.VideosFragment
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var layout: View
    lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private val READ_STORAGE_PERMISSION_REQUEST_CODE = 100
    private val REQUEST_CODE = 200
    private val REQUEST_CODE1 = 124
    private val mIntentListener: Interfaces? = null

    private val listofFragment = arrayOf(
        ImagesFragment(),
        VideosFragment(), DocsFragment()
    )

    private val fragmentNames = arrayOf(
        "Images",
        "Videos", "Docs"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
        permissionChecker()
        tabLayoutListener()

    }

    private fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        layout = binding.root

        viewPager = binding.pager
        tabLayout = binding.tabs
    }

    private fun permissionChecker() {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already available
            setStatePageAdapter()
        } else {
            // Permission is missing and must be requested.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestCameraPermission()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {

            showSnackBar(R.string.storage_permission_check, Snackbar.LENGTH_INDEFINITE, R.string.ok)

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.READ_EXTERNAL_STORAGE,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    READ_STORAGE_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == READ_STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setStatePageAdapter()
            } else {
                showSnackBar(
                    R.string.storage_permission_check,
                    Snackbar.LENGTH_INDEFINITE,
                    R.string.ok
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            mIntentListener?.onIntent(data, resultCode)
        } else if (requestCode == REQUEST_CODE1) {
            mIntentListener?.onIntent1(data, resultCode)
        }
    }

    fun setStatePageAdapter() {
        val adapter = AdapterTabPager(this)

        for (item in listofFragment.indices) {
            adapter.addFragment(listofFragment[item], fragmentNames[item])
        }
        viewPager.adapter = adapter
        viewPager.currentItem = 0
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = adapter.getTabTitle(position)
        }.attach()
    }

    private fun tabLayoutListener() {
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

    class AdapterTabPager(activity: FragmentActivity?) : FragmentStateAdapter(activity!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()

        public fun getTabTitle(position: Int): String {
            return mFragmentTitleList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getItemCount(): Int {
            return mFragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return mFragmentList[position]
        }
    }

    private fun showSnackBar(permissionCheck: Int, lengthLong: Int, actionText: Int) {
        val snackBar =
            Snackbar.make(layout, permissionCheck, lengthLong)
                .setAction(actionText) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                            arrayOf(
                                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                            ),
                            READ_STORAGE_PERMISSION_REQUEST_CODE
                        )
                    }
                }
        snackBar.show()
    }
}