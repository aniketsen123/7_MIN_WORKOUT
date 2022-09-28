package com.example.a7minworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minworkout.databinding.ActivityExerciseBinding
import com.example.a7minworkout.databinding.ExerciseLayoutBinding

class ExerciseAdapter(val items:ArrayList<ExerciseModel>):RecyclerView.Adapter<ExerciseAdapter.Viewholder>() {

    class Viewholder(binding: ExerciseLayoutBinding):RecyclerView.ViewHolder(binding?.root)
    {
        val tvitem=binding?.ivtextview
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        return Viewholder(ExerciseLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
       val model:ExerciseModel=items[position]
        holder.tvitem.text=model.getId().toString()

        when{
            model.getisSelected()->{
holder.tvitem.background=ContextCompat.getDrawable(holder.tvitem.context,R.drawable.circular_border_selected)
                holder.tvitem.setTextColor(Color.parseColor("#212121"))
            }
            model.getisCompleted()->{
                holder.tvitem.background=ContextCompat.getDrawable(holder.tvitem.context,R.drawable.circular_border_done)
                holder.tvitem.setTextColor(Color.parseColor("#212121"))
            }
            else->
            {
                holder.tvitem.background=ContextCompat.getDrawable(holder.tvitem.context,R.drawable.circular_border_not_done)
                holder.tvitem.setTextColor(Color.parseColor("#212121"))
            }
        }
    }

    override fun getItemCount(): Int {
      return   items.size
    }
}