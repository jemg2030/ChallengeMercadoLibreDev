package com.example.challengemercadolibredev.mvvmmecadolibre.view.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengemercadolibredev.R
import com.example.challengemercadolibredev.mvvmmecadolibre.utils.State
import com.example.challengemercadolibredev.mvvmmecadolibre.view.`interface`.OpenItemDetail
import com.example.challengemercadolibredev.mvvmmecadolibre.view.adapter.SearchAdapter
import com.example.challengemercadolibredev.mvvmmecadolibre.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.ivSearchOff
import kotlinx.android.synthetic.main.activity_search.pbLoader
import kotlinx.android.synthetic.main.activity_search.tvBodyProductOff
import kotlinx.android.synthetic.main.activity_search.tvTitleProductOff
import java.lang.Exception

class SearchActivity : AppCompatActivity(), OpenItemDetail {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter : SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        initView()
        initViewModel()
        observeViewModel()
        searchListener()
        getExtras(savedInstanceState)
    }

    private fun initViewModel(){
        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
    }

    private fun observeViewModel(){
        searchViewModel.search.observe(this, {

            searchAdapter.setData(it.results as ArrayList)
        })

        searchViewModel.status.observe(this, {
            when(it){
                State.DONE->{
                    pbLoader.visibility = GONE
                    ivSearchOff.visibility = GONE
                    tvTitleProductOff.visibility = GONE
                    tvBodyProductOff.visibility = GONE
                    rvProductsResults.visibility = VISIBLE
                    setResult(200 )

                }
                State.LOADING->{
                    pbLoader.visibility = VISIBLE
                    rvProductsResults.visibility = GONE
                    ivSearchOff.visibility = GONE
                    tvTitleProductOff.visibility = GONE
                    tvBodyProductOff.visibility = GONE

                }
                State.ERROR->{
                    pbLoader.visibility = GONE
                    rvProductsResults.visibility = GONE
                    ivSearchOff.visibility = VISIBLE
                    tvTitleProductOff.visibility = VISIBLE
                    tvBodyProductOff.visibility = VISIBLE
                }

            }
        })
    }
    private fun searchAPI(query : String){
        searchViewModel.let {
            it.getSearch(query)
        }
    }

    private fun initView(){
        searchAdapter = SearchAdapter(this)
        rvProductsResults.layoutManager = LinearLayoutManager(this)
        rvProductsResults.adapter = searchAdapter
    }

    private fun searchListener(){
        searchProduct.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                searchAPI(query)
                return false
            }

        })
    }

    private fun getExtras(savedInstanceState: Bundle?) {
        try {
            if (savedInstanceState==null)
                intent.getStringExtra("query")?.let {
                    searchAPI(it)
                    searchProduct.isIconified = false
                    searchProduct.setQuery(it,false)
                    searchProduct.clearFocus()
                }
        }catch (e: Exception){

        }
    }

    override fun openDetailActivity(id: String) {
        val intent : Intent = Intent(this, ProductDetailActivity::class.java).putExtra("id",id )
        startActivity(intent)
    }
}