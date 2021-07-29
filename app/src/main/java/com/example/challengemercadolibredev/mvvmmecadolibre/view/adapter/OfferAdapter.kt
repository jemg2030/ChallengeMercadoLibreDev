package com.example.challengemercadolibredev.mvvmmecadolibre.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengemercadolibredev.R
import com.example.challengemercadolibredev.mvvmmecadolibre.model.*
import kotlinx.android.synthetic.main.item_offer.view.*

class OfferAdapter() : RecyclerView.Adapter<OfferAdapter.ViewHolder>() {

    var listResult: ArrayList<Item> = arrayListOf()

    fun setData(listResult: ArrayList<Item>) {
        this.listResult = listResult;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_offer, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = listResult.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listResult[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("StringFormatMatches")
        fun bindItems(item: Item) {
            itemView.tvTitleProductOffer.text = item.title
            itemView.tvPriceOffer.text = "$ ${item.price.value}"
            itemView.tvDiscountOffer.text= item.discount.text
            Glide.with(itemView.context).load(item.picture.url).into(itemView.ivPictureOffer);
        }
    }
}