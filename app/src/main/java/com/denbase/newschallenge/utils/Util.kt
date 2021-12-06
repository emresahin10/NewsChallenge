package com.denbase.newschallenge.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.downloadImage(url: String?){
    Glide.with(this).load(url).centerCrop().into(this)
}

fun View.hideView(){
    this.visibility = View.GONE
}

fun View.showView(){
    this.visibility = View.VISIBLE
}