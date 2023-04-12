package com.auchtermuchty.jav1001_final_exam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.auchtermuchty.jav1001_final_exam.Model.Die
import com.auchtermuchty.jav1001_final_exam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var die: Die
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        die = Die(6,1,false)

        binding.btnRollOnce.setOnClickListener {
            binding.txtResult.text = die.roll()
        }

        binding.btnRollTwice.setOnClickListener {
            val firstResult = die.roll()
            val secondResult = die.roll()
            binding.txtResult.text = firstResult + secondResult
        }

    }
}