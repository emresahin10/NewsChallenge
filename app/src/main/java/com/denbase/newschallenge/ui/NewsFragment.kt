package com.denbase.newschallenge.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denbase.newschallenge.R
import com.denbase.newschallenge.adapter.NewsAdapter
import com.denbase.newschallenge.databinding.FragmentNewsBinding
import com.denbase.newschallenge.utils.Resource
import com.denbase.newschallenge.utils.hideView
import com.denbase.newschallenge.utils.showView
import com.denbase.newschallenge.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsFragment : Fragment(R.layout.fragment_news) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var recyclerViewAdapter: NewsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private lateinit var sourceId: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsBinding.bind(view)

        sourceId = arguments?.getString("sourceId").toString()
        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        setupAdapter()

        recyclerViewAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_newsFragment_to_newsDetailFragment, bundle)
        }
        getNews()

    }

    private fun setupAdapter() {
        recyclerViewAdapter = NewsAdapter()
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvNews.apply {
            layoutManager = linearLayoutManager
            adapter = recyclerViewAdapter
        }
    }

    private fun getNews(){
        viewModel.getNewsWithSource(sourceId)
        viewModel.newsResponse.observe(viewLifecycleOwner){ response ->

            when(response){
                is Resource.Loading -> {
                    binding.pbNews.showView()
                }
                is Resource.Success -> {
                    binding.pbNews.hideView()
                    response.data?.let {
                        recyclerViewAdapter.differ.submitList(it.articles)
                        //recyclerViewAdapter.notifyDataSetChanged()
                    }
                }
                is Resource.Error -> {
                    binding.pbNews.showView()
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