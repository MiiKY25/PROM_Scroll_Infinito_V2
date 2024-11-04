package com.mikel.scroll_infinito

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter para gestionar la lista de tareas en un RecyclerView.
 *
 * @param tasks Lista de tareas a mostrar en el RecyclerView.
 * @param onItemDone Función que se ejecuta al completar una tarea, recibe la posición del elemento.
 */
class TaskAdapter(private val tasks: MutableList<Task>, private val onItemDone: (Int) -> Unit) : RecyclerView.Adapter<TaskViewHolder>() {

    /**
     * Crea un nuevo ViewHolder para una tarea.
     *
     * @param parent El ViewGroup al que se adjuntará el nuevo ViewHolder.
     * @param viewType Tipo de vista del nuevo ViewHolder.
     * @return Una nueva instancia de TaskViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        // Infla el diseño del item de tarea
        return TaskViewHolder(layoutInflater.inflate(R.layout.item_task, parent, false))
    }

    /**
     * Devuelve la cantidad total de tareas en la lista.
     *
     * @return El número de elementos en la lista de tareas.
     */
    override fun getItemCount() = tasks.size

    /**
     * Asigna los datos a un ViewHolder en una posición específica.
     *
     * @param holder El ViewHolder que se actualizará con la tarea.
     * @param position La posición de la tarea en la lista.
     */
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.render(tasks[position].tarea, onItemDone) // Renderiza la tarea en el ViewHolder
    }
}
