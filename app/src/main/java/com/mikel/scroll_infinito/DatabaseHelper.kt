package com.mikel.scroll_infinito

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Clase de datos que representa una tarea.
 *
 * @property id El identificador único de la tarea.
 * @property tarea El texto de la tarea.
 */
data class Task(
    val id: Long,
    val tarea: String
)

/**
 * Ayudante de base de datos para gestionar las operaciones de la base de datos de tareas.
 *
 * Esta clase extiende SQLiteOpenHelper para manejar la creación y actualización de la base de datos.
 *
 * @param context El contexto de la aplicación.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "tasks.db" // Nombre de la base de datos
        private const val DATABASE_VERSION = 1 // Versión de la base de datos
        private const val TABLA_TAREA = "tasks" // Nombre de la tabla de tareas
        private const val COLUMN_ID = "id" // Nombre de la columna para el ID
        private const val COLUMN_TAREA = "tarea" // Nombre de la columna para la tarea
    }

    /**
     * Método llamado al crear la base de datos.
     * Crea la tabla de tareas.
     *
     * @param db La base de datos SQLite donde se crearán las tablas.
     */
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLA_TAREA ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_TAREA TEXT)"
        db.execSQL(createTable)
    }

    /**
     * Método llamado al actualizar la base de datos.
     * Elimina la tabla anterior y crea una nueva.
     *
     * @param db La base de datos SQLite que se está actualizando.
     * @param oldVersion La versión anterior de la base de datos.
     * @param newVersion La nueva versión de la base de datos.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_TAREA")
        onCreate(db)
    }

    /**
     * Agrega una nueva tarea a la base de datos.
     *
     * @param tarea El texto de la tarea a agregar.
     * @return El ID de la tarea recién insertada.
     */
    fun addTarea(tarea: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply { put(COLUMN_TAREA, tarea) }
        return db.insert(TABLA_TAREA, null, values)
    }

    /**
     * Recupera todas las tareas de la base de datos.
     *
     * @return Una lista mutable de todas las tareas.
     */
    fun getTodasTareas(): MutableList<Task> {
        val tasks = mutableListOf<Task>()
        val db = readableDatabase
        val cursor = db.query(TABLA_TAREA, arrayOf(COLUMN_ID, COLUMN_TAREA), null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val id = getLong(getColumnIndexOrThrow(COLUMN_ID))
                val tarea = getString(getColumnIndexOrThrow(COLUMN_TAREA))
                tasks.add(Task(id, tarea))
            }
            close()
        }
        return tasks
    }

    /**
     * Elimina una tarea de la base de datos.
     *
     * @param id El ID de la tarea a eliminar.
     */
    fun deleteTarea(id: Long) {
        val db = writableDatabase
        db.delete(TABLA_TAREA, "$COLUMN_ID=?", arrayOf(id.toString()))
    }
}
