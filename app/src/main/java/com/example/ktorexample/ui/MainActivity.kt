package com.example.ktorexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ktorexample.R
import com.example.ktorexample.adapter.PostAdapter
import com.example.ktorexample.databinding.ActivityMainBinding
import com.example.ktorexample.util.ApiState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels()

    @Inject
    lateinit var postAdapter: PostAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        mainViewModel.getPost()
        lifecycleScope.launchWhenStarted {
            mainViewModel.apiStateFlow.collect {
                binding.apply {
                    when (it) {
                        is ApiState.Sucess -> {
                            recyclerview.isVisible = true
                            progressBar.isVisible = false
                            error.isVisible = false
                            postAdapter.submitList(it.data)
                        }
                        is ApiState.Failure -> {
                            recyclerview.isVisible = false
                            progressBar.isVisible = false
                            error.isVisible = true
                            error.text = it.msg.toString()
                        }
                        is ApiState.Loading -> {
                            recyclerview.isVisible = false
                            progressBar.isVisible = true
                            error.isVisible = false
                        }
                        is ApiState.Empty -> {

                        }
                    }
                }

            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            recyclerview.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = postAdapter
            }
        }
    }
}