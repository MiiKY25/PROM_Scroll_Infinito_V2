package com.mikel.scroll_infinito

import android.content.Context
import android.content.SharedPreferences

/**
 * La clase Preferences se encarga de manejar el almacenamiento y recuperación
 * de tareas utilizando SharedPreferences.
 *
 * @property prefs La instancia de SharedPreferences utilizada para guardar y obtener datos.
 * @constructor Crea una nueva instancia de Preferences.
 *
 * @param context El contexto de la aplicación.
 */
class Preferences(context: Context) {

    companion object {
        const val PREFS_NAME = "myDatabase" // Nombre del archivo de SharedPreferences
        const val TASKS = "tasks_value" // Clave para almacenar la lista de tareas
    }

    // Inicializa SharedPreferences
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    /**
     * Guarda una lista de tareas en SharedPreferences.
     *
     * @param tasks La lista de tareas a guardar.
     */
    fun saveTasks(tasks: List<String>) {
        // Convierte la lista de tareas en un conjunto y lo guarda
        prefs.edit().putStringSet(TASKS, tasks.toSet()).apply()
    }

    /**
     * Recupera la lista de tareas almacenada en SharedPreferences.
     *
     * @return Una lista mutable de tareas. Devuelve una lista vacía si no hay tareas guardadas.
     */
    fun getTasks(): MutableList<String> {
        // Obtiene el conjunto de tareas y lo convierte en una lista mutable
        return prefs.getStringSet(TASKS, emptySet<String>())?.toMutableList() ?: mutableListOf()
    }
}
