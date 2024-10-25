package com.mikel.scroll_infinito

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikel.scroll_infinito.TaskApplication.Companion.prefs

class MainActivity : AppCompatActivity() {

    lateinit var btnAddTask:Button
    lateinit var etTask:EditText
    lateinit var rvTasks:RecyclerView

    lateinit var adapter: TaskAdapter
    lateinit var mediaPlayer: MediaPlayer // Declarar el MediaPlayer

    var tasks = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()

        // Inicializar MediaPlayer con el archivo de sonido
        mediaPlayer = MediaPlayer.create(this, R.raw.delete_sound)
    }

    private fun initUi(){
        initView()
        initListeners()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        tasks = prefs.getTasks()
        rvTasks.layoutManager = LinearLayoutManager(this)
        adapter = TaskAdapter(tasks) { deleteTask(it) }
        rvTasks.adapter=adapter
    }

    private fun deleteTask(position: Int){
        tasks.removeAt(position)
        adapter.notifyDataSetChanged()
        prefs.saveTasks(tasks)

        // Reproducir sonido de eliminación
        mediaPlayer.start()
    }

    private fun initListeners(){
        btnAddTask.setOnClickListener{ addTask() }
    }

    private fun addTask(){
        val taskToAdd:String = etTask.text.toString()
        if (!taskToAdd.isEmpty()){
            tasks.add(taskToAdd)
            prefs.saveTasks(tasks)
            adapter.notifyDataSetChanged()
            etTask.setText("")
        }
    }

    private fun initView(){
        btnAddTask = findViewById(R.id.btn_aniadirTarea)
        etTask = findViewById(R.id.txt_tarea)
        rvTasks = findViewById(R.id.View_listaTarea)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Liberar el MediaPlayer cuando la actividad se destruya
        mediaPlayer.release()
    }
}
