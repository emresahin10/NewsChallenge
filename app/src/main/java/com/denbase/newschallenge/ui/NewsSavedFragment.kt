package com.denbase.newschallenge.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.denbase.newschallenge.R
import com.denbase.newschallenge.adapter.NewsAdapter
import com.denbase.newschallenge.databinding.FragmentNewsSavedBinding
import com.denbase.newschallenge.utils.hideView
import com.denbase.newschallenge.utils.showView
import com.denbase.newschallenge.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsSavedFragment : Fragment(R.layout.fragment_news_saved) {


    private lateinit var viewModel: NewsViewModel
    private lateinit var recyclerViewAdapter: NewsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var _binding: FragmentNewsSavedBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsSavedBinding.bind(view)

        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        setupAdapter()

        recyclerViewAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putBoolean("isSaved", true)
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_newsSavedFragment_to_newsDetailFragment, bundle)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
            recyclerViewAdapter.differ.submitList(articles)
            if (articles.isEmpty()){
                binding.lwEmptyView.showView()
            }
        })

    }

    private fun setupAdapter() {
        recyclerViewAdapter = NewsAdapter()
        linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rvSavedNews.apply {
            layoutManager = linearLayoutManager
            adapter = recyclerViewAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}