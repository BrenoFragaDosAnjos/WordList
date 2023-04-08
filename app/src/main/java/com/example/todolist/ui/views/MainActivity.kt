package com.example.todolist.ui.views

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.application.WordApplication
import com.example.todolist.database.models.Word
import com.example.todolist.databinding.ActivityMainBinding
import com.example.todolist.ui.adapters.WordListAdapter
import com.example.todolist.ui.viewModels.WordViewModel
import com.example.todolist.ui.viewModels.WordViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : WordListAdapter
    private lateinit var binding : ActivityMainBinding


    private val viewModel : WordViewModel by viewModels {
        WordViewModelFactory((application as WordApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerview
        adapter = WordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        initObservables()
        initWord()
    }

    private fun initWord() {
        var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                it.data?.let {
                    it.getStringExtra("word")?.let { it1 -> Word(it1) }
                        ?.let { it2 -> viewModel.insert(it2) }
                }
            }
        }
        binding.fab.setOnClickListener {
            resultLauncher.launch(Intent(this@MainActivity,NewWordActivity::class.java))
        }
    }

    private fun initObservables() {
        viewModel.allWords.observe(this, Observer {
            it?.let { adapter.submitList(it) }
        })
    }
}