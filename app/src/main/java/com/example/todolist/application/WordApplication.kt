package com.example.todolist.application

import android.app.Application
import com.example.todolist.database.WordDatabase
import com.example.todolist.repositories.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { WordDatabase.getDatabase(applicationScope,this) }
    val repository by lazy { WordRepository(database.wordDao()) }
}