package com.example.challengemercadolibredev.mvvmmecadolibre.view.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengemercadolibredev.R
import com.example.challengemercadolibredev.mvvmmecadolibre.model.DetailProduct
import com.example.challengemercadolibredev.mvvmmecadolibre.utils.State
import com.example.challengemercadolibredev.mvvmmecadolibre.view.adapter.PictureAdapter
import com.example.challengemercadolibredev.mvvmmecadolibre.viewmodel.DetailProductViewModel
import kotlinx.android.synthetic.main.activity_product_detail.*
import java.lang.Exception

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var detailProductViewModel: DetailProductViewModel
    private lateinit var pictureAdapter: PictureAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)
        initView()
        initViewModel()
        observeViewModel()
        getExtras(savedInstanceState)
    }

    private fun initViewModel(){
        detailProductViewModel = ViewModelProvider(this).get(DetailProductViewModel::class.java)
    }

    private fun observeViewModel(){
        detailProductViewModel.details.observe(this, Observer {
            pictureAdapter.setData(it.pictures as ArrayList)
            setDataView(it)
        })

        detailProductViewModel.status.observe(this, Observer {
            when(it){
                State.DONE->{
                    pbLoader.visibility = GONE
                    ivSearchOff.visibility = GONE
                    tvTitleProductOff.visibility = GONE
                    tvBodyProductOff.visibility = GONE
                    container.visibility = VISIBLE

                }
                State.LOADING->{
                    pbLoader.visibility = VISIBLE
                    container.visibility = GONE
                    ivSearchOff.visibility = GONE
                    tvTitleProductOff.visibility = GONE
                    tvBodyProductOff.visibility = GONE
                }
                State.ERROR->{
                    pbLoader.visibility = GONE
                    container.visibility = GONE
                    ivSearchOff.visibility = VISIBLE
                    tvTitleProductOff.visibility = VISIBLE
                    tvBodyProductOff.visibility = VISIBLE
                }

            }
        })
    }

    private fun getItemAPI(id : String){
        detailProductViewModel.let {
            it.getItem(id)
        }
    }
    private fun initView(){
        pictureAdapter = PictureAdapter()
        rvPictures.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        rvPictures.adapter = pictureAdapter
    }

    private fun setDataView(detailProductModel: DetailProduct){
        tvProductName.text = detailProductModel.title
        if (detailProductModel.condition.equals("new")) tvProductStatus.text = getString(R.string.new_state) else tvProductStatus.text = getString(R.string.used_state)
        tvPrice.text = "$ ${detailProductModel.price}"
        if (!detailProductModel.shipping.free_shipping) tvShipping.visibility= GONE
        tvSeller.text ="${detailProductModel.available_quantity}/${detailProductModel.initial_quantity}"
        tvCountProduct.text ="${detailProductModel.pictures.size}/${detailProductModel.pictures.size}"
    }
    private fun getExtras(savedInstanceState: Bundle?) {
        try {
            if (savedInstanceState==null)
                intent.getStringExtra("id")?.let {
                    getItemAPI(it)
                }
        }catch (e: Exception){

        }
    }

}