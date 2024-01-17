package com.example.scrachcard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

internal class ImageListAdapter(private var imageslist: List<ImageModel>,private var context: Context) :
    RecyclerView.Adapter<ImageListAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var image: ImageView = view.findViewById(R.id.image_)

    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.imageitem, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val image = imageslist[position]
        Glide.with(context).load(image.getImage()).into(holder.image)
    }
    override fun getItemCount(): Int {
        return imageslist.size
    }
}