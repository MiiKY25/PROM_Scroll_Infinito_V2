package com.mikel.scroll_infinito

import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * MainActivity es la actividad principal de la aplicación que permite al usuario gestionar una lista de tareas.
 * Proporciona funcionalidades para agregar, eliminar y mostrar tareas, así como reproducir un sonido al agregar o eliminar una tarea.
 */
class MainActivity : AppCompatActivity() {

    lateinit var btnAddTask: Button // Botón para añadir una nueva tarea
    lateinit var etTask: EditText // Campo de texto para ingresar la tarea
    lateinit var rvTasks: RecyclerView // RecyclerView para mostrar la lista de tareas
    lateinit var adapter: TaskAdapter // Adaptador para gestionar la lista de tareas
    private var mediaPlayer: MediaPlayer? = null // Declarar el MediaPlayer para reproducir sonidos
    var tasks = mutableListOf<Task>() // Lista mutable que contiene las tareas

    /**
     * Se llama cuando se crea la actividad.
     * Inicializa la interfaz de usuario y el MediaPlayer.
     *
     * @param savedInstanceState Estado guardado de la actividad, si existe.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()

        // Inicializar MediaPlayer con el archivo de sonido
        mediaPlayer = MediaPlayer.create(this, R.raw.add_task_sound)
    }

    /**
     * Inicializa la interfaz de usuario, incluyendo vistas, oyentes de eventos y el RecyclerView.
     */
    private fun initUi() {
        initView()
        initListeners()
        initRecyclerView()
    }

    /**
     * Inicializa el RecyclerView con la lista de tareas.
     * Configura el adaptador y el administrador de diseño.
     */
    private fun initRecyclerView() {
        tasks = (application as TaskApplication).dbHelper.getTodasTareas() // Carga las tareas desde la base de datos
        rvTasks.layoutManager = LinearLayoutManager(this) // Configura el diseño lineal
        adapter = TaskAdapter(tasks) { deleteTask(it) } // Crea el adaptador con la lista de tareas
        rvTasks.adapter = adapter // Asigna el adaptador al RecyclerView
        attachSwipeToDelete() // Activa el deslizamiento para eliminar
    }

    /**
     * Elimina una tarea de la lista en la posición especificada.
     * Reproduce un sonido de eliminación después de eliminar la tarea.
     *
     * @param position Posición de la tarea a eliminar.
     */
    private fun deleteTask(position: Int) {
        val task = tasks[position]
        tasks.removeAt(position) // Elimina la tarea de la lista
        adapter.notifyDataSetChanged() // Notifica al adaptador que los datos han cambiado
        (application as TaskApplication).dbHelper.deleteTarea(task.id) // Elimina de la base de datos
        playDeleteTaskSound()
    }

    /**
     * Inicializa los oyentes de eventos para las vistas.
     * En particular, configura el oyente del botón para añadir tareas.
     */
    private fun initListeners() {
        btnAddTask.setOnClickListener { addTask() }
    }

    /**
     * Agrega una nueva tarea a la lista si el campo de texto no está vacío.
     * Actualiza el RecyclerView y guarda la tarea en la base de datos.
     */
    private fun addTask() {
        val taskToAdd = etTask.text.toString().trim() // Obtiene el texto de la tarea
        if (taskToAdd.isNotEmpty()) { // Verifica que el campo no esté vacío
            val dbHelper = (application as TaskApplication).dbHelper
            val taskId = dbHelper.addTarea(taskToAdd) // Inserta en la base de datos
            val newTask = Task(id = taskId, tarea = taskToAdd)

            tasks.add(newTask) // Añade la nueva tarea a la lista
            adapter.notifyDataSetChanged() // Notifica al adaptador que los datos han cambiado
            etTask.setText("") // Limpia el campo de texto
            playAddTaskSound()
        } else {
            etTask.error = "Escribe una tarea"
            playDeleteTaskSound()
        }
    }

    /**
     * Reproduce un sonido al añadir una tarea, liberando el MediaPlayer después de que se complete la reproducción.
     */
    private fun playAddTaskSound() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, R.raw.add_task_sound)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { it.release() }
    }

    /**
     * Reproduce un sonido al eliminar una tarea, liberando el MediaPlayer después de que se complete la reproducción.
     */
    private fun playDeleteTaskSound() {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, R.raw.delete_task_sound)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener { it.release() }
    }

    /**
     * Inicializa las vistas y enlaza los elementos de la interfaz de usuario.
     */
    private fun initView() {
        btnAddTask = findViewById(R.id.btn_aniadirTarea) // Enlaza el botón de añadir tarea
        etTask = findViewById(R.id.txt_tarea) // Enlaza el campo de texto
        rvTasks = findViewById(R.id.View_listaTarea) // Enlaza el RecyclerView
    }

    /**
     * Se llama cuando la actividad se destruye.
     * Libera los recursos utilizados por el MediaPlayer.
     */
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Libera el MediaPlayer cuando la actividad se destruya
    }

    /**
     * Configura el gesto de deslizamiento para eliminar una tarea.
     */
    private fun attachSwipeToDelete() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // No se utiliza el movimiento vertical
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    val itemView = viewHolder.itemView // Obtiene la vista del ítem
                    itemView.setBackgroundColor(Color.RED) // Cambia el fondo a rojo
                    itemView.translationX = dX // Traducción en X
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                deleteTask(position) // Llama a la función de eliminación
            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT) // Restablece el fondo
            }
        }

        // Asocia el ItemTouchHelper al RecyclerView
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(rvTasks)
    }
}
