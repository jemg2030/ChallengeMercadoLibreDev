package com.example.challengemercadolibredev.mvvmmecadolibre.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengemercadolibredev.mvvmmecadolibre.api.Api
import com.example.challengemercadolibredev.mvvmmecadolibre.model.DetailProduct
import com.example.challengemercadolibredev.mvvmmecadolibre.utils.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailProductViewModel : ViewModel() {

    private var _details = MutableLiveData<DetailProduct>()
    val details: LiveData<DetailProduct>
        get() = _details

    private var _status = MutableLiveData<State>()
    val status: LiveData<State>
        get() = _status

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun getItem(id: String) {
        coroutineScope.launch {

            _status.value = State.LOADING
            var getDetail = Api.retrofitService.getItem(id)

            try {
                var results = getDetail.await()
                results.let {
                    _details.value = results
                    _status.value = State.DONE
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = State.ERROR
            }
        }
    }

    fun getHome() {

    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}