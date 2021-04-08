package com.widya.submission1bfaa.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.widya.submission1bfaa.R
import com.widya.submission1bfaa.ViewModul.DetailViewModel
import com.widya.submission1bfaa.databinding.ActivityDetailUserBinding

class DetailUser : AppCompatActivity() {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var binding: ActivityDetailUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        var username = intent.getStringExtra(EXTRA_USER)
        setData(username!!)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabslayout)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TABLAYOUT_TITLE[position])

        }.attach()
    }

    private fun setData(username: String) {
        detailViewModel.setDetailUser(username)
        detailViewModel.getDetailUser().observe(this, { userData ->
            if (userData != null) {
                with(binding) {
                    Glide.with(this@DetailUser)
                        .load(userData.avatar)
                        .into(imgAvatar)
                    supportActionBar?.title = username
                    txtUsername.text = userData.username as String
                    txtName.text = userData.name as String
                    txtCompany.text = userData.company as String
                    txtRepository.text = userData.repository as String
                    txtLocation.text = userData.location as String
                    txtFollowers.text = userData.followers as String
                    txtFollowing.text = userData.following as String
                    showLoading(false)
                }
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()

    }

    companion object {
        const val EXTRA_USER = "extra_user"
        private val TABLAYOUT_TITLE = intArrayOf(
            R.string.following,
            R.string.followers
        )
    }
}