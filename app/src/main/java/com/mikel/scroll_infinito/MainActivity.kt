package com.mikel.scroll_infinito

import android.icu.text.Transliterator.Position
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var btnAddTask:Button
    lateinit var etTask:EditText
    lateinit var rvTasks:RecyclerView

    lateinit var adapter: TaskAdapter

    var tasks = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()

    }

    private fun initUi(){
        initView()
        initListeners()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rvTasks.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(tasks) { deleteTask(it) }
        rvTasks.adapter=adapter
    }

    private fun deleteTask(position: Int){
        tasks.removeAt(position)
        adapter.notifyDataSetChanged()
    }

    private fun initListeners(){
        btnAddTask.setOnClickListener{ addTask() }
    }

    private fun addTask(){
        val taskToAdd:String = etTask.text.toString()
        tasks.add(taskToAdd)
        adapter.notifyDataSetChanged()
        etTask.setText("")
    }

    private fun initView(){
        btnAddTask = findViewById(R.id.btn_aniadirTarea)
        etTask = findViewById(R.id.txt_tarea)
        rvTasks = findViewById(R.id.View_listaTarea)
    }
}