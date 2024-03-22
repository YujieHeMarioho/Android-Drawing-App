package com.example.finaldraw

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import com.example.finaldraw.Drawing


class DrawingRepository(private val scope: CoroutineScope, private val dao: DrawingDao) {

    // Calls the dao to get all the facts stored in the database
    val allDrawings: Flow<List<Drawing>> = dao.getAllDrawings()

    suspend fun insertDrawing(drawing: Drawing) {
        dao.insert(drawing);
    }

    suspend fun deleteDrawing(drawing: Drawing) {
        dao.delete(drawing);
    }

    suspend fun deleteAllDrawings() {
        dao.deleteAllDrawings()
    }
}