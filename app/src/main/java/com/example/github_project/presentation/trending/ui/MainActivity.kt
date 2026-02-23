package com.example.github_project.presentation.trending.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.github_project.databinding.ActivityMainBinding
import com.example.github_project.presentation.trending.adapter.RepoAdapter
import com.example.github_project.presentation.trending.viewmodel.TrendingViewModel
import com.example.github_project.presentation.trending.viewmodel.UiEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: TrendingViewModel by viewModels()
    private val adapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        // 1) Collect PagingData
        lifecycleScope.launch {
            viewModel.repos.collect { pagingData ->
                adapter.submitData(pagingData)
            }
        }

        // 2) LoadState = loading/error
        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadStates ->
                val isLoading = loadStates.refresh is LoadState.Loading
                val isError = loadStates.refresh is LoadState.Error

                binding.progress.isVisible = isLoading
                binding.recycler.isVisible = !isLoading && !isError
                binding.errorText.isVisible = isError

                if (isError) {
                    val err = (loadStates.refresh as LoadState.Error).error
                    binding.errorText.text = err.message ?: "Unknown error"
                }
            }
        }

        // 3) Events (Toast)
        lifecycleScope.launch {
            viewModel.events.collect { event ->
                when (event) {
                    is UiEvent.ShowToast ->
                        Toast.makeText(this@MainActivity, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}