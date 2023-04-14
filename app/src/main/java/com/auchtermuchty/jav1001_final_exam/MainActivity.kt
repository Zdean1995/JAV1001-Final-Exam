package com.auchtermuchty.jav1001_final_exam

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.auchtermuchty.jav1001_final_exam.model.Die
import com.auchtermuchty.jav1001_final_exam.model.getDieFromString
import com.auchtermuchty.jav1001_final_exam.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var die: Die
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = this.getPreferences(Context.MODE_PRIVATE)
        die = if(pref.contains("die")){
            getDieFromString(pref.getString("die", null)!!)
        } else {
            Die(6, 1, false)
        }

        binding.etxtSides.setText((die.sides).toString())
        binding.etxtMulti.setText((die.multiple).toString())


        binding.btnRollOnce.setOnClickListener {
            if(!nullCheck()) {
                val result = die.roll()
                binding.txtResult.text = resources.getString(R.string.roll_once_result, result)
                binding.txtSecondResult.text = ""
            }
        }

        binding.btnRollTwice.setOnClickListener {
            if(!nullCheck()) {
                val firstResult = die.roll()
                val secondResult = die.roll()
                binding.txtResult.text =
                    resources.getString(R.string.roll_once_result, firstResult)
                binding.txtSecondResult.text =
                    resources.getString(R.string.roll_twice_result, secondResult)
            }
        }

        binding.etxtSides.addTextChangedListener(sidesWatcher)
        binding.etxtMulti.addTextChangedListener(multiWatcher)
    }

    //https://www.geeksforgeeks.org/ontextchangedlistener-in-android/
    private var sidesWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // this function is called before text is edited
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // this function is called when text is edited
            if(s.toString() == "") {
                die.sides = null
            } else {
                die.sides = s.toString().toInt()
            }
        }

        override fun afterTextChanged(s: Editable) {
            // this function is called after text is edited
        }
    }

    private var multiWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // this function is called before text is edited
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if(s.toString() == "") {
                die.multiple = null
            } else {
                die.multiple = s.toString().toInt()
            }
        }

        override fun afterTextChanged(s: Editable) {
            // this function is called after text is edited
        }
    }
    private fun nullCheck() : Boolean{
        var dieHasNullValues = false
        if(die.multiple == null || die.multiple == 0) {
            val toast = Toast.makeText(this, "Please enter a multiplication value", Toast.LENGTH_SHORT)
            toast.show()
            dieHasNullValues = true
        }
        if(die.sides == null || die.sides == 0) {
            val toast = Toast.makeText(this, "Please enter a value for sides", Toast.LENGTH_SHORT)
            toast.show()
            dieHasNullValues = true
        }
        return dieHasNullValues
    }

    override fun onStop() {
        super.onStop()
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("die", die.toString())
            apply()
        }
    }
}