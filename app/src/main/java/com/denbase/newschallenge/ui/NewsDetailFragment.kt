package com.denbase.newschallenge.ui

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.denbase.newschallenge.R
import com.denbase.newschallenge.databinding.FragmentNewsDetailBinding
import com.denbase.newschallenge.utils.downloadImage
import com.denbase.newschallenge.utils.hideView
import com.denbase.newschallenge.utils.showView
import com.denbase.newschallenge.viewmodels.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsDetailFragment : Fragment(R.layout.fragment_news_detail) {

    private lateinit var viewModel: NewsViewModel
    private var _binding: FragmentNewsDetailBinding? = null
    private val args: NewsDetailFragmentArgs by navArgs()
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewsDetailBinding.bind(view)

        viewModel = ViewModelProvider(requireActivity()).get(NewsViewModel::class.java)
        val article = args.article
        val isSaved = arguments?.getBoolean("isSaved")

        binding.apply {
            txtArticleDetailTitle.text = article.title
            txtArticleDetailDescription.text = article.description
            imgArticleDetailImage.downloadImage(article.urlToImage)
            btnDetail.setOnClickListener {
                wvArticleDetail.showView()
                if (article.url != null){
                    wvArticleDetail.apply {
                        webViewClient = WebViewClient()
                        //settings.javaScriptEnabled = true
                        loadUrl(article.url)
                    }
                }
            }
            //Delete article and hide bookmark
            if (isSaved == true){
                imgArticleDetailSave.hideView()
                imgArticleDetailDelete.showView()
                imgArticleDetailDelete.setOnClickListener {
                    viewModel.deleteArticle(article)
                    Toast.makeText(requireContext(),getString(R.string.delete_save_news),Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding.imgArticleDetailSave.setOnClickListener {
            viewModel.saveArticle(article)
            Toast.makeText(requireContext(),getString(R.string.add_save_news),Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}