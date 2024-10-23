package com.mikel.scroll_infinito

import android.app.ActivityManager.AppTask
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val task:List<String>):RecyclerView.Adapter<TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater:LayoutInflater = LayoutInflater.from(parent.context)
        return TaskViewHolder(layoutInflater.inflate(R.layout.item_task,parent,false))
    }

    override fun getItemCount() = task.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.render(task[position])
    }


}