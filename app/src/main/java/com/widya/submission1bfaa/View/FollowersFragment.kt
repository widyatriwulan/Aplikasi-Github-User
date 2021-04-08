package com.widya.submission1bfaa.View

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.widya.submission1bfaa.ViewModul.MainViewModel
import com.widya.submission1bfaa.Model.User
import com.widya.submission1bfaa.databinding.FragmentFollowersBinding


class FollowersFragment : Fragment() {

    private lateinit var adapter: UserAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        binding.lvFollowers.setHasFixedSize(true)
        showRecyclerList()
    }

    private fun showRecyclerList() {
        binding.lvFollowers.layoutManager = LinearLayoutManager(requireContext())
        binding.lvFollowers.adapter = adapter

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        mainViewModel.setFollowers(Username)
        mainViewModel.getFollowers().observe(requireActivity(), { userItems ->
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

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showDetail(data: User) {
        Username = data.username.toString()
        FollowingFragment.Username = data.username.toString()
        val intentDetail = Intent(requireContext(), DetailUser::class.java)
        intentDetail.putExtra(DetailUser.EXTRA_USER, data.username)
        startActivity(intentDetail)
    }

    companion object {
        var Username = "username"
    }

}