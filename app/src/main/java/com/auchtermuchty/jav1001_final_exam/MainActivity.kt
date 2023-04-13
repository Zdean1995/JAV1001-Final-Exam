package com.auchtermuchty.jav1001_final_exam

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.auchtermuchty.jav1001_final_exam.Model.Die
import com.auchtermuchty.jav1001_final_exam.Model.getDieFromString
import com.auchtermuchty.jav1001_final_exam.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var die: Die
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.etxtSides.setText((die.sides).toString())
        binding.etxtMulti.setText((die.multiple).toString())


        binding.btnRollOnce.setOnClickListener {
            val result = die.roll()
            binding.txtResult.text = resources.getString(R.string.roll_once_result, result)
        }

        binding.btnRollTwice.setOnClickListener {
            val firstResult = die.roll()
            val secondResult = die.roll()
            binding.txtResult.text = resources.getString(R.string.roll_twice_result, firstResult, secondResult)
        }

        binding.etxtSides.addTextChangedListener(sidesWatcher)
        binding.etxtMulti.addTextChangedListener(multiWatcher)
    }

    //https://www.geeksforgeeks.org/ontextchangedlistener-in-android/
    var sidesWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // this function is called before text is edited
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // this function is called when text is edited
            try {
                die.sides = s.toString().toInt()
            } catch (e: Exception) {
                binding.etxtSides.setText("1")
                die.multiple = 1
            }
        }

        override fun afterTextChanged(s: Editable) {
            // this function is called after text is edited
        }
    }

    var multiWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // this function is called before text is edited
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            try {
                die.multiple = s.toString().toInt()
            } catch (e: Exception) {
                binding.etxtMulti.setText("1")
                die.multiple = 1
            }
        }

        override fun afterTextChanged(s: Editable) {
            // this function is called after text is edited
        }
    }
    fun nullCheck(){
        die.sides = binding.etxtSides.text.toString().toInt()
    }

    override fun onStop() {
        super.onStop()
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("die", die.toString())
            apply()
        }
    }

    override fun onStart() {
        super.onStart()
        val pref = this.getPreferences(Context.MODE_PRIVATE)
        die = if(pref.contains("die")){
            getDieFromString(pref.getString("die", null)!!)
        } else {
            Die(6, 1, false)
        }
    }
}