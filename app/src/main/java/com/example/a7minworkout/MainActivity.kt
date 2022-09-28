package com.example.a7minworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.a7minworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        //constraint layout root of entire binding object
        binding?.flStart?.setOnClickListener{
            Toast.makeText(this,"Here We Go",Toast.LENGTH_SHORT).show()
            val intent=Intent(this,ExerciseActivity::class.java)
            startActivity(intent)
        }
        binding?.flbmi?.setOnClickListener{
            val intent1=Intent(this,bmiactivity::class.java)
            startActivity(intent1)
        }
        binding?.history?.setOnClickListener{
            val intent2=Intent(this,history::class.java)
            startActivity(intent2)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding=null
    }
}