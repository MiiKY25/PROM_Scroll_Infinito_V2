package com.mikel.scroll_infinito

import android.app.Application

/**
 * Clase personalizada de aplicación para inicializar preferencias compartidas.
 *
 * Esta clase extiende Application y se utiliza para almacenar una instancia global de Preferences
 * que se puede acceder desde cualquier parte de la aplicación.
 */
class TaskApplication : Application() {

    lateinit var dbHelper: DatabaseHelper

    /**
     * Método llamado al crear la aplicación.
     * Inicializa la instancia de Preferences utilizando el contexto de la aplicación.
     */
    override fun onCreate() {
        super.onCreate()
        dbHelper = DatabaseHelper(this) // Inicializa el helper de la base de datos
    }
}
