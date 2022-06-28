package br.com.zup.appsimples.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.zup.appsimples.R
import br.com.zup.appsimples.databinding.ActivityMainBinding
import br.com.zup.appsimples.model.MainModel
import br.com.zup.appsimples.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observers()
        viewModel.getSavedData()

        binding.btnEntrar.setOnClickListener {

            getData()
            Toast.makeText(this, "Entrou!", Toast.LENGTH_LONG).show()
        }
    }

    private fun getData(){
        val string = binding.etString.text.toString()
        val int = binding.etInt.text.toString().toInt()
        val mainData = MainModel(string,int)

        viewModel.getData(mainData,binding.swOnOff.isChecked)
    }

    private fun observers(){
        viewModel.savedData.observe(this){
            binding.etString.setText(it.string)
            if(it.int == 1234567890){
                binding.etInt.setText("")
            }
            else{
                binding.etInt.setText(it.int.toString())
            }

        }
        viewModel.savedDataFlag.observe(this){
            binding.swOnOff.isChecked = it
        }
    }
}