package com.example.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.a7minworkout.databinding.ActivityFinishactivityBinding
import kotlinx.coroutines.launch
import java.text.Format
import java.text.SimpleDateFormat
import java.util.*

class finishactivity : AppCompatActivity() {
    private var binding:ActivityFinishactivityBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFinishactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbar)
        if(supportActionBar!=null)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding?.toolbar?.setNavigationOnClickListener{
            onBackPressed()
        }
        binding?.button?.setOnClickListener {
            finish()
        }
        val dao =(application as WOrkoutApp).db.historyDoa()
        addDatetodatabase(dao)
    }
    private fun addDatetodatabase(historydao: historydao)
    {
        val c=Calendar.getInstance()
        val datetime=c.time
        val sdf=SimpleDateFormat("dd MMM yyyy   hh:mm:ss",Locale.getDefault())
        val date=sdf.format(datetime)
        lifecycleScope.launch{
            historydao.insert(historyentity(date))
        }
    }
}