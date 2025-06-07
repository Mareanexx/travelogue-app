package ru.mareanexx.data.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.mareanexx.data.db.TravelogueDatabase
import javax.inject.Inject

class DatabaseCleaner @Inject constructor(private val database: TravelogueDatabase) {

    suspend fun cleanAll() {
        withContext(Dispatchers.IO) {
            database.clearAllTables()
        }
    }
}