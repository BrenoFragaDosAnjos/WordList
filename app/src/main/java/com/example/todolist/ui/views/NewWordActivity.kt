package com.example.todolist.ui.views

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.example.todolist.R
import com.example.todolist.databinding.ActivityNewWordBinding

class NewWordActivity : AppCompatActivity() {
    private lateinit var binding : ActivityNewWordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewWordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() {
        with(binding){
            buttonSave.setOnClickListener {
                val intent  = Intent()
                if(TextUtils.isEmpty(editWord.text)){
                    setResult(Activity.RESULT_CANCELED,intent)
                }else{
                    var word = editWord.text.toString()
                    intent.putExtra("word",word)
                    setResult(Activity.RESULT_OK,intent)
                }
                finish()
            }
        }
    }
}