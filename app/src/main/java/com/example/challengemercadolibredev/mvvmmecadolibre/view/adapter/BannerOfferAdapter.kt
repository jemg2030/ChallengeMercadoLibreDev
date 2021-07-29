package com.example.challengemercadolibredev.mvvmmecadolibre.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengemercadolibredev.R
import com.example.challengemercadolibredev.mvvmmecadolibre.model.Element
import kotlinx.android.synthetic.main.item_banner_offer.view.*

class BannerOfferAdapter() : RecyclerView.Adapter<BannerOfferAdapter.ViewHolder>() {

    var listResult: ArrayList<Element> = arrayListOf()

    fun setData(listResult: ArrayList<Element>) {
        this.listResult = listResult;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_banner_offer, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = listResult.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listResult[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("StringFormatMatches")
        fun bindItems(element: Element) {
            Glide.with(itemView.context).load(element.picture.url.src).into(itemView.imgSrc);
        }
    }
}