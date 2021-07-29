package com.example.challengemercadolibredev.mvvmmecadolibre.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.challengemercadolibredev.R
import com.example.challengemercadolibredev.mvvmmecadolibre.view.`interface`.OpenItemDetail
import com.example.challengemercadolibredev.mvvmmecadolibre.model.Result
import kotlinx.android.synthetic.main.item_product.view.*

class SearchAdapter(val openItemDetail: OpenItemDetail) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    var listResult: ArrayList<Result> = arrayListOf()

    fun setData(listResult: ArrayList<Result>) {
        this.listResult = listResult;
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = listResult.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(listResult[position])
        holder.itemView.container.setOnClickListener {
            openItemDetail.openDetailActivity(listResult[position].id)
        }


    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("StringFormatMatches")
        fun bindItems(result: Result) {
            itemView.tvPrice.text = "$ ${result.price}"
            itemView.tvProductName.text = result.title
            itemView.tvFees.text = "en ${result.installments.quantity}x $ ${result.installments.amount}"
            if (result.shipping.free_shipping){
                itemView.tvSeller.visibility = VISIBLE
            }else{
                itemView.tvSeller.visibility = GONE
            }
            Glide.with(itemView.context).load(result.thumbnail).into(itemView.ivProduct)
        }
    }
}