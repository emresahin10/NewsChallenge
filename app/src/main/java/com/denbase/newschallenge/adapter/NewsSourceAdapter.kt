package com.denbase.newschallenge.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.denbase.newschallenge.R
import com.denbase.newschallenge.data.model.NewsSources
import com.denbase.newschallenge.databinding.RvItemSourcesBinding

class NewsSourceAdapter(var newsSourceList: List<NewsSources>): RecyclerView.Adapter<NewsSourceAdapter.ViewHolder>() {

     class ViewHolder(binding: RvItemSourcesBinding): RecyclerView.ViewHolder(binding.root){
         val sourceTitle = binding.txtSourceTitle
         val sourceDescription = binding.txtSourceDescription
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RvItemSourcesBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       newsSourceList.get(position).let {
           holder.apply {
               sourceTitle.text = it.name
               sourceDescription.text = it.description
           }
       }
        holder.itemView.setOnClickListener {
            val sourceId = newsSourceList.get(position).id
            val sourceName = newsSourceList.get(position).name

            it.findNavController().navigate(R.id.action_newsSourcesFragment_to_newsFragment, bundleOf(
                "sourceId" to sourceId,
                "sourceName" to sourceName
            ))
        }
    }

    override fun getItemCount(): Int {
        return newsSourceList.size
    }

}