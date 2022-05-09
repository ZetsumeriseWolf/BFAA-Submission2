package com.dicoding.submission2

import android.app.SearchManager
import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submission2.adapter.UserAdapter
import com.dicoding.submission2.data.User
import com.dicoding.submission2.databinding.ActivityMainBinding
import com.dicoding.submission2.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userQuery: String
    private lateinit var userAdapter: UserAdapter
    private lateinit var viewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        userAdapter = UserAdapter()
        binding.recycleView.apply {
            adapter = userAdapter
            if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                layoutManager = GridLayoutManager(this@MainActivity,2)
            } else {
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.svUser

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_username)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(q: String?): Boolean {
                userQuery = q.toString()
                Toast.makeText(this@MainActivity, q, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()
                viewModel.getUserData(userQuery).observe(this@MainActivity) {
                    when (it) {
                        is Resource.Error -> onErrorMainActivity(it.message)
                        is Resource.Success -> it.data?.let { dataUser -> onSuccessMainActivity(dataUser) }
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    private fun onSuccessMainActivity(data: List<User>) {
        userAdapter.setAllData(data)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun onErrorMainActivity(message: String?) {}

}