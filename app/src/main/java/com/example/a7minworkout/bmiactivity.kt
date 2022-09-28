package com.example.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minworkout.databinding.ActivityBmiactivityBinding
import java.math.BigDecimal
import java.math.RoundingMode

class bmiactivity : AppCompatActivity() {
    companion object{
        private const val METRIC_UNIT_VIEW="METRIC_UNIT_VIEW"
        private const val US_UNIT_VIEW="US_UNIT_VIEW"
    }
    private var currentvisible:String= METRIC_UNIT_VIEW
    private var binding:ActivityBmiactivityBinding ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.bmitool)
        if(supportActionBar!=null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
             supportActionBar?.setTitle("CALCULATE BMI")
        }
        binding?.bmitool?.setNavigationOnClickListener{
            onBackPressed()
        }

        makemetricvisible()
        binding?.radiogrp?.setOnCheckedChangeListener{_,checkedId:Int->
            if(checkedId==R.id.metriunits)
                makemetricvisible()
            else
                usunitvisible()
        }
        binding?.btnCalculateUnits?.setOnClickListener {
            cal()
        }
    }
    private fun cal() {
        if (currentvisible == METRIC_UNIT_VIEW) {

                if (valid()) {
                    val heightValue: Float =
                        binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                    val weighvalue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                    val bmi = weighvalue / (heightValue * heightValue)
                    calculate(bmi)
                } else {
                    Toast.makeText(this, "ENTER THE DETAILS", Toast.LENGTH_SHORT).show()
                }
            }

        else {

                if (valid()) {
                    val weighvalue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                    val feet: Float = binding?.etUsMetricUnitHeightFeet?.text.toString().toFloat()
                    val inch: Float = binding?.etUsMetricUnitHeightInch?.text.toString().toFloat()
                    val height = feet * 12 + inch
                    val bmi = 703 * (weighvalue / (height * height))
                    calculate(bmi)
                }
                else {
                    Toast.makeText(this, "ENTER THE DETAILS", Toast.LENGTH_SHORT).show()
                }
            }
        }
    private fun makemetricvisible()
    {
        currentvisible= METRIC_UNIT_VIEW
        binding?.tilMetricUnitHeight?.visibility=View.VISIBLE
       binding?.tilMetricUnitWeight?.visibility=View.VISIBLE
        binding?.llUs?.visibility=View.INVISIBLE
        binding?.etUsMetricUnitHeightInch?.text!!.clear()
        binding?.etUsMetricUnitHeightFeet?.text!!.clear()
        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.llDiplayBMIResult?.visibility=View.INVISIBLE
    }
    private fun usunitvisible()
    {
        currentvisible= US_UNIT_VIEW
        binding?.tilMetricUnitHeight?.visibility=View.INVISIBLE
        binding?.tilMetricUnitWeight?.visibility=View.VISIBLE
        binding?.llUs?.visibility=View.VISIBLE
        binding?.etUsMetricUnitHeightInch?.text!!.clear()
        binding?.etUsMetricUnitHeightFeet?.text!!.clear()
        binding?.etMetricUnitHeight?.text!!.clear()
        binding?.llDiplayBMIResult?.visibility=View.INVISIBLE
    }
    private fun calculate(bmi:Float)
    {
        val bmilabel:String
        val bmidescreption:String
        if(bmi.compareTo(15f)<=0)
        {
            bmilabel="You Are Very Severly UnderWeight"
            bmidescreption="Oops!You need to take care of yourself.Eat More!!"
        }
        else  if(bmi.compareTo(16f)<=0)
        {
            bmilabel="You Are  Severly UnderWeight"
            bmidescreption="Oops!You need to take care of yourself.Eat More!!"
        }
        else  if(bmi.compareTo(18.5f)<=0)
        {
            bmilabel="You Are  Severly UnderWeight"
            bmidescreption="Oops!You need to take care of yourself.Eat More!!"
        }
        else  if(bmi.compareTo(25f)<=0)
        {
            bmilabel="Normal Weight"
            bmidescreption="Congratulation!You Are In Good Shape"
        }
        else  if(bmi.compareTo(30f)<=0)
        {
            bmilabel="OverWeight"
            bmidescreption="Oops!You need to take care of yourself.Workout!!"
        }
        else  if(bmi.compareTo(35f)<=0)
        {
            bmilabel="Obese Class | (Moderately Obese)"
            bmidescreption="Oops!You need to take care of yourself.Workout!!"
        }
        else  if(bmi.compareTo(40f)<=0)
        {
            bmilabel="Obese Class | (Severely Obese)"
            bmidescreption="Oops!You need to take care of yourself.Workout!!"
        }
        else
        {
            bmilabel="Obese Class | (Very Severely Obese)"
            bmidescreption="Oops!You need to take care of yourself.Workout!!"
        }
        val bmivalue=BigDecimal(bmi.toDouble()).setScale(2,RoundingMode.HALF_EVEN).toString()
       binding?.llDiplayBMIResult?.visibility=View.VISIBLE
        binding?.tvBMIValue?.text=bmivalue
        binding?.tvBMIValue?.text=bmivalue
        binding?.tvBMIType?.text=bmidescreption
    }
    private fun valid():Boolean
    {
        var isValid=true
        if(binding?.etMetricUnitWeight.toString().isEmpty())
            isValid=false
        else if(binding?.etMetricUnitHeight.toString().isEmpty())
            isValid=false
        return isValid
    }
}