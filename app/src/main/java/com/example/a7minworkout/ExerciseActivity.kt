package com.example.a7minworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minworkout.databinding.ActivityExerciseBinding
import com.example.a7minworkout.databinding.ActivityMainBinding
import com.example.a7minworkout.databinding.CloseBinding
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
     private var binding:ActivityExerciseBinding?=null
    private var restTimer:CountDownTimer?=null
    private var restProgress=0
    private var exerciseTimer:CountDownTimer?=null
    private var exerciseProgress=0
    private var exerciselist:ArrayList<ExerciseModel>?=null
    private var currentpos=-1;
    private var tts: TextToSpeech?=null
    private var exerciseadapter:ExerciseAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
exerciselist=Constants.defaultExerciseList()
        setSupportActionBar(binding?.toolbarExercise)
        if(supportActionBar!=null)
        {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        tts= TextToSpeech(this,this)
        binding?.toolbarExercise?.setNavigationOnClickListener{
            customDialogForback()
        }

        setUpRestView()
       // binding?.ffprogress?.visibility=View.GONE
        setupExercisestatus()

    }
    private fun setupExercisestatus()
    {
        binding?.rvExerciseStatus?.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        exerciseadapter= ExerciseAdapter(exerciselist!!)
        binding?.rvExerciseStatus?.adapter=exerciseadapter
    }
    private fun setUpRestView()
    {


        binding?.flRestView?.visibility=View.VISIBLE
        binding?.tvTitle?.visibility=View.VISIBLE
        binding?.tvExerciseName?.visibility=View.GONE
        binding?.flExerciseView?.visibility=View.GONE
        binding?.ivImage?.visibility=View.GONE
        binding?.tvupcomingExerciseName?.visibility=View.VISIBLE
        binding?.tvupcomingExerciseName2?.visibility=View.VISIBLE
        if(restTimer!=null)
        {
            restTimer!!.cancel()
            restProgress=0;
        }
          binding?.tvupcomingExerciseName2?.text=exerciselist!![currentpos+1].getname()
            setProgress()

    }
    private fun setupexerciseview()
    {
        binding?.flRestView?.visibility=View.GONE
        binding?.tvTitle?.visibility=View.GONE
        binding?.tvExerciseName?.visibility=View.VISIBLE
        binding?.flExerciseView?.visibility=View.VISIBLE
        binding?.ivImage?.visibility=View.VISIBLE
        binding?.tvupcomingExerciseName?.visibility=View.GONE
        binding?.tvupcomingExerciseName2?.visibility=View.GONE
        if(exerciseTimer!=null)
        {
            exerciseTimer!!.cancel()
            exerciseProgress=0;
        }
        speakout(exerciselist!![currentpos].getname())

        binding?.ivImage?.setImageResource(exerciselist!![currentpos].getimage())
        binding?.tvExerciseName?.text=exerciselist!![currentpos].getname()
   setExerciseProgress()
    }
    private fun setProgress()
    {
        binding?.progressBar?.progress=restProgress
        restTimer=object :CountDownTimer(10000,1000)
        {
            override fun onTick(p0: Long) {
                restProgress++
                binding?.progressBar?.progress=10-restProgress
                binding?.tvTimer?.text=(10-restProgress).toString()
            }

            override fun onFinish() {
                Toast.makeText(this@ExerciseActivity,"Exercise Starts Now",Toast.LENGTH_SHORT).show()
                currentpos++;
                exerciselist!![currentpos].setisSelected(true)
                exerciseadapter!!.notifyDataSetChanged()
                setupexerciseview()
            }

        }.start()
    }
    private fun customDialogForback()
    {
        val customDialog= Dialog(this)
        val dialogbinding=CloseBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogbinding?.root)
        customDialog.setCanceledOnTouchOutside(false)
        dialogbinding.button2.setOnClickListener {
            this.finish()
            customDialog.dismiss()
        }
        dialogbinding.button3.setOnClickListener {
            customDialog.dismiss()
        }
        customDialog.show()
    }

    override fun onBackPressed() {
        customDialogForback()

    }
    private fun setExerciseProgress()
    {
        binding?.progressBarExercise?.progress=exerciseProgress
        exerciseTimer=object :CountDownTimer(30000,1000)
        {
            override fun onTick(p0: Long) {
                exerciseProgress++
                binding?.progressBarExercise?.progress=30-exerciseProgress
                binding?.tvTimerExercise?.text=(30-exerciseProgress).toString()
            }

            override fun onFinish() {
                if(currentpos<exerciselist?.size!!-1)
                {   exerciselist!![currentpos].setisSelected(false)
                    exerciselist!![currentpos].setisCompleted(true)
                    Toast.makeText(this@ExerciseActivity,"30 sec are over ",Toast.LENGTH_SHORT).show()
                    setUpRestView()}
                else
                {Toast.makeText(this@ExerciseActivity,"7 mins finished ",Toast.LENGTH_SHORT).show()
                finish()
                    val intent=Intent(this@ExerciseActivity,finishactivity::class.java)
                    startActivity(intent)
                }
            }

        }.start()
    }
    override fun onDestroy() {
        super.onDestroy()
        if(restTimer!=null)
        {
            restTimer!!.cancel()
            restProgress=0;
        }
        if(exerciseTimer!=null)
        {
            exerciseTimer!!.cancel()
                 exerciseProgress=0
        }
        if(tts!=null)
        {    tts!!.stop()
            tts!!.shutdown()
        }
        binding=null
    }

    override fun onInit(status: Int) {
        if(status==TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
                Log.d("language", "Langyage not supported")
        }
        else
            Log.d("language", "Wrong")
        }



private fun speakout(text:String)
{
    //Queue mode where all entries in the playback queue (media to be played and text to be synthesized) are dropped and replaced by the new entry
    tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
}
}