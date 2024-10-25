package com.mikel.scroll_infinito

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * ViewHolder para una tarea en el RecyclerView.
 *
 * Este ViewHolder mantiene las referencias a las vistas de una tarea específica
 * y gestiona su presentación y comportamiento.
 *
 * @property tvTask La vista de texto que muestra el nombre de la tarea.
 * @property ivTaskDone La vista de imagen que indica si la tarea ha sido completada.
 * @param view La vista que representa un item de tarea.
 */
class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val tvTask: TextView = view.findViewById(R.id.tvTask) // Inicializa el TextView para la tarea
    private val ivTaskDone: ImageView = view.findViewById(R.id.ivTaskDone) // Inicializa el ImageView para el estado de la tarea

    /**
     * Renderiza la tarea en las vistas del ViewHolder.
     *
     * @param task La tarea que se mostrará en el TextView.
     * @param onItemDone Función que se ejecuta cuando se hace clic en el icono de completado.
     */
    fun render(task: String, onItemDone: (Int) -> Unit) {
        tvTask.text = task // Establece el texto del TextView con el nombre de la tarea
        ivTaskDone.setOnClickListener { onItemDone(adapterPosition) } // Configura el listener para el clic en el icono
    }
}
