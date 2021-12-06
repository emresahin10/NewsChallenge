package com.denbase.newschallenge.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.denbase.newschallenge.R
import com.denbase.newschallenge.adapter.NewsSourceAdapter
import com.denbase.newschallenge.databinding.FragmentNewsSourcesBinding
import com.denbase.newschallenge.utils.Resource
import com.denbase.newschallenge.utils.hideView
import com.denbase.newschallenge.utils.showView
import com.denbase.newschallenge.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsSourcesFragment : Fragment(R.layout.fragment_news_sources) {

    private lateinit var recyclerViewAdapter: NewsSourceAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var viewModel:NewsViewModel
    private var _binding: FragmentNewsSourcesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsSourcesBinding.bind(view)

        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)

        setupAdapter()
        getSources()
    }

    private fun setupAdapter(){
        recyclerViewAdapter = NewsSourceAdapter(listOf())
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvSources.apply {
            layoutManager = linearLayoutManager
            adapter = recyclerViewAdapter
        }
    }

    private fun getSources(){
        viewModel.getSources()
        viewModel.newsSourceResponse.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Loading -> {
                    binding.pbSource.showView()
                }
                is Resource.Success -> {
                    binding.pbSource.hideView()
                    response.data?.let {
                        recyclerViewAdapter.newsSourceList = it.sources
                        recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    binding.pbSource.showView()
                    Log.e("error", response.message.toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}