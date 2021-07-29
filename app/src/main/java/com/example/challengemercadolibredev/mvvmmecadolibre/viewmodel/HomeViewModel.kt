package com.example.challengemercadolibredev.mvvmmecadolibre.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challengemercadolibredev.mvvmmecadolibre.model.ComponentsJson
import com.example.challengemercadolibredev.mvvmmecadolibre.utils.LoadHome
import com.example.challengemercadolibredev.mvvmmecadolibre.utils.State
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class HomeViewModel : ViewModel() {

    private var _components = MutableLiveData<ComponentsJson>()
    val components: LiveData<ComponentsJson>
        get() = _components

    private var _status = MutableLiveData<State>()
    val status: LiveData<State>
        get() = _status

    private val viewModelJob = Job()
    private val coroutineScope = CoroutineScope( viewModelJob + Dispatchers.Main )


    fun getHome(context: Context){
        try {
            _status.value = State.LOADING
            _components.value = Gson().fromJson(LoadHome.loadJSONFromAsset(context), ComponentsJson::class.java)
            _status.value = State.DONE
        }catch (e : java.lang.Exception){
            e.printStackTrace()
            _status.value = State.ERROR
        }

    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}