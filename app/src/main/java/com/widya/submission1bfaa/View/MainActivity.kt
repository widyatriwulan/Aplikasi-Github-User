package com.widya.submission1bfaa.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.widya.submission1bfaa.ViewModul.MainViewModel
import com.widya.submission1bfaa.Model.User
import com.widya.submission1bfaa.R
import com.widya.submission1bfaa.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.lvList.setHasFixedSize(true)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        showRecyclerList()
        showRecyclerListSearch()

        binding.searchView.apply {
            setOnSearchClickListener {
                this.onActionViewExpanded()
                search()
            }

            setOnClickListener {
                this.onActionViewExpanded()
                search()
            }
        }
    }

    private fun search() {
        binding.searchView.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    mainViewModel.setDataByUsername(query)
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isNotEmpty()) {
                        mainViewModel.setDataByUsername(newText)
                        showLoading(true)
                    } else {
                        showRecyclerList()
                        showLoading(true)
                    }
                    return true
                }
            })
        }
    }

    private fun showRecyclerList() {
        binding.lvList.layoutManager = LinearLayoutManager(this)
        binding.lvList.adapter = adapter

        mainViewModel.setUserGithub()
        mainViewModel.getUserGithub().observe(this, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showDetail(data)
            }
        })
    }

    private fun showRecyclerListSearch() {
        binding.lvList.layoutManager = LinearLayoutManager(this)
        binding.lvList.adapter = adapter

        mainViewModel.setUserGithub()
        mainViewModel.getSearchUser().observe(this, { userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: User) {
                showDetail(data)
            }
        })
    }

    private fun showDetail(data: User) {
        FollowingFragment.Username = data.username.toString()
        FollowersFragment.Username = data.username.toString()

        val intentDetail = Intent(this@MainActivity, DetailUser::class.java)
        intentDetail.putExtra(DetailUser.EXTRA_USER, data.username)
        startActivity(intentDetail)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}
