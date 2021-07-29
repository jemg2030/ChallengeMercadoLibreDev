package com.example.challengemercadolibredev.mvvmmecadolibre.view.activitys

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengemercadolibredev.R
import com.example.challengemercadolibredev.mvvmmecadolibre.utils.State
import com.example.challengemercadolibredev.mvvmmecadolibre.view.adapter.BannerOfferAdapter
import com.example.challengemercadolibredev.mvvmmecadolibre.view.adapter.OfferAdapter
import com.example.challengemercadolibredev.mvvmmecadolibre.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var bannerOfferAdapter: BannerOfferAdapter
    private lateinit var offerAdapter: OfferAdapter
    private var scroll : Int = 0;
    private val timer: CountDownTimer by lazy {
        object : CountDownTimer(60000, 4000) {
            override fun onFinish() {
                timer.start()
            }

            override fun onTick(millisUntilFinished: Long) {
                if (scroll == 4){
                    rvProducts.smoothScrollToPosition(scroll);
                    scroll = 0
                }else {
                    rvProducts.smoothScrollToPosition(scroll);
                    scroll ++
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
        initViewModel()
        observeViewModel()
        searchListener()
        if (savedInstanceState==null) homeViewModel.getHome(this)
    }

    private fun initViewModel(){
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
    }

    private fun observeViewModel(){
        homeViewModel.components.observe(this, Observer {
            it.components.forEach {
                if (it.type.equals("exhibitors")){
                    timer.start()
                    bannerOfferAdapter.setData(it.elements as ArrayList)
                }else if (it.type.equals("recommendations")){
                    offerAdapter.setData(it.items as ArrayList)

                }
            }
        })

        homeViewModel.status.observe(this, Observer {
            when(it){
                State.DONE->{
                    pbLoader.visibility = GONE
                    rvProducts.visibility = VISIBLE
                    cvPromotionsContainer.visibility = VISIBLE

                }
                State.LOADING->{
                    pbLoader.visibility = VISIBLE
                    rvProducts.visibility = GONE
                    cvPromotionsContainer.visibility = GONE


                }
                State.ERROR->{
                    pbLoader.visibility = GONE
                    rvProducts.visibility = GONE
                    cvPromotionsContainer.visibility = GONE

                    Toast.makeText(this,"Ha ocurrido un error", Toast.LENGTH_SHORT).show()
                }

            }
        })
    }

    private fun initView(){
        bannerOfferAdapter = BannerOfferAdapter()
        rvProducts.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        rvProducts.adapter = bannerOfferAdapter
        offerAdapter = OfferAdapter()
        rvReminders.layoutManager = GridLayoutManager(this,2)
        rvReminders.adapter = offerAdapter
    }

    private fun searchListener(){
        searchProduct.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                openSearchActivity(query)
                return false
            }

        })
    }

    private fun openSearchActivity(query: String){
        val intent : Intent = Intent(this, SearchActivity::class.java).putExtra("query",query )
        startActivityForResult(intent, 200)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200){
            searchProduct.setQuery("", false)
            searchProduct.isIconified = true;
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }
}