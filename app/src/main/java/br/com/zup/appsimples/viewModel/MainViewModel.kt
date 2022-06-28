package br.com.zup.appsimples.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.zup.appsimples.FLAG_KEY
import br.com.zup.appsimples.INT_KEY
import br.com.zup.appsimples.PREF_KEY
import br.com.zup.appsimples.STRING_KEY
import br.com.zup.appsimples.model.MainModel
import br.com.zup.appsimples.repository.MainRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MainRepository()

    private val _savedData: MutableLiveData<MainModel> = MutableLiveData()
    val savedData: LiveData<MainModel> = _savedData

    private val _savedDataFlag: MutableLiveData<Boolean> = MutableLiveData()
    val savedDataFlag: LiveData<Boolean> = _savedDataFlag

    val pref = application.getSharedPreferences(PREF_KEY, Context.MODE_PRIVATE)
    val prefEditor = pref.edit()

    fun getData(mainData: MainModel, flagSwitch: Boolean){
        try {
            prefEditor.putBoolean(FLAG_KEY, flagSwitch)
            prefEditor.apply()

            if(flagSwitch){
                prefEditor.putString(STRING_KEY,mainData.string)
                prefEditor.putInt(INT_KEY,mainData.int)
                prefEditor.apply()
            }else{
                prefEditor.remove(STRING_KEY)
                prefEditor.remove(INT_KEY)
                prefEditor.apply()
            }
            _savedData.value = repository.getData(mainData)
        }catch (ex: Exception){
            Log.i("Error", "------> ${ex.message}")
        }
    }

    fun getSavedData(){
        try {
            val string = pref.getString(STRING_KEY,"").toString()
            val int = pref.getInt(INT_KEY,1234567890)
            val mainData = MainModel(string,int)
            _savedData.value = mainData
            _savedDataFlag.value = pref.getBoolean(FLAG_KEY,false)
        }catch (ex: Exception){
            Log.i("Error", "------> ${ex.message}")
        }
    }

}