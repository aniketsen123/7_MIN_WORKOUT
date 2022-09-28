package com.example.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minworkout.databinding.ActivityHistoryBinding
import kotlinx.coroutines.launch

class history : AppCompatActivity() {
    private var binding:ActivityHistoryBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.historytool)
        if(supportActionBar!=null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setTitle("HISTORY")
        }
        binding?.historytool?.setNavigationOnClickListener{
            onBackPressed()
        }
        val dao =(application as WOrkoutApp).db.historyDoa()
        getAllCompleteDate(dao)
    }
    fun getAllCompleteDate(historydao: historydao) {
        lifecycleScope.launch {
            historydao.fetchAlldates().collect() { getAllCompleteDate ->
                if (getAllCompleteDate.isNotEmpty()) {
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility = View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility = View.VISIBLE

                    binding?.rvHistory?.layoutManager = LinearLayoutManager(this@history)
                    val data=ArrayList<String>()
                    for (i in getAllCompleteDate) {
                       data.add(i.date)
                    }
                    val historyadapter=historyadapter(data)
                    binding?.rvHistory?.adapter=historyadapter
                }
                else
                    binding?.tvHistory?.visibility=View.GONE
                binding?.rvHistory?.visibility=View.GONE
                binding?.tvNoDataAvailable?.visibility=View.GONE
                }
            }
        }
    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}