package com.example.a7minworkout

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.a7minworkout.databinding.ActivityHistoryBinding
import com.example.a7minworkout.databinding.ItemHistoryBinding

class historyadapter(private val items:List<String>): RecyclerView.Adapter<historyadapter.Viweholder>() {


    class Viweholder(binding:ItemHistoryBinding):RecyclerView.ViewHolder(binding.root)
    {
        val llhistoryitemmain=binding.llHistoryItemMain
        val tvitem=binding.tvItem
        val tvposition=binding.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viweholder {
        return Viweholder(ItemHistoryBinding.inflate(LayoutInflater.from(parent.context),parent,false ))
    }

    override fun onBindViewHolder(holder: Viweholder, position: Int) {
        val date:String=items.get(position)
        holder.tvitem.text=date
        holder.tvposition.text=(position+1).toString()
        if(position%2==0)
            holder.llhistoryitemmain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.white))
        else
            holder.llhistoryitemmain.setBackgroundColor(Color.parseColor("#FFFFFF  "))

    }

    override fun getItemCount(): Int {
        return items.size
    }
}