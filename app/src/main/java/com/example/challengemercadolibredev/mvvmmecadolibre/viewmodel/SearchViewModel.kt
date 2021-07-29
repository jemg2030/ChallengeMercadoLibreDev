package com.example.challengemercadolibredev.mvvmmecadolibre.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengemercadolibredev.mvvmmecadolibre.api.Api
import com.example.challengemercadolibredev.mvvmmecadolibre.utils.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import com.example.challengemercadolibredev.mvvmmecadolibre.model.Search

class SearchViewModel : ViewModel() {

    private var _search = MutableLiveData<Search>()
    val search: LiveData<Search>
        get() = _search

    private var _status = MutableLiveData<State>()
    val status: LiveData<State>
        get() = _status

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope( viewModelJob + Dispatchers.Main )

    fun getSearch(query : String){
        coroutineScope.launch {

            _status.value = State.LOADING
            var getSearch = Api.retrofitService.getSearch(query)

            try {
                var results = getSearch.await()
                if (results.results.isNotEmpty()){
                    _search.value =  results
                    _status.value = State.DONE
                }else{
                    _status.value = State.ERROR
                }
            }catch (e: Exception){
                e.printStackTrace()
                _status.value = State.ERROR
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}