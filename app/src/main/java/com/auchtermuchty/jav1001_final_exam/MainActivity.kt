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

        //gets information about the die from preferences when the activity is created
        val pref = this.getPreferences(Context.MODE_PRIVATE)
        die = if(pref.contains("die")){
            getDieFromString(pref.getString("die", null)!!)
        } else {
            Die(6, 1, false)
        }

        //sets the ui to the die's attributes
        binding.etxtSides.setText((die.sides).toString())
        binding.etxtMulti.setText((die.multiple).toString())
        binding.swtTrueCount.isChecked = die.trueCount

        //the onclicklistener for the roll once button.
        //also clears the second result text view if the roll twice button was
        //pressed before
        binding.btnRollOnce.setOnClickListener {
            if(!nullCheck()) {
                val result = die.roll()
                binding.txtResult.text = resources.getString(R.string.roll_once_result, result)
                binding.txtSecondResult.text = ""
            }
        }

        //the onclicklistener for the roll twice button
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

        //the onclicklistener for the true count switch
        binding.swtTrueCount.setOnClickListener {
            die.trueCount = binding.swtTrueCount.isChecked
        }

        //assigning the watchers to the edittexts
        binding.etxtSides.addTextChangedListener(sidesWatcher)
        binding.etxtMulti.addTextChangedListener(multiWatcher)
    }

    //the click watcher for the edit texts.
    // https://www.geeksforgeeks.org/ontextchangedlistener-in-android/
    private var sidesWatcher: TextWatcher = object : TextWatcher {
        //the beforeTextChanged and afterTextChanged methods aren't
        // used but overriding them is required
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            /*
            this is so the edittext can be blank without crashing.  It's also why the properties
            for the die class are nullable. Originally I had it so that when the edittexts were
            cleared they were reset to 1.  This felt a little awkward to uses so I set this up.
            */
            if(s.toString() == "") {
                die.sides = null
            } else {
                die.sides = s.toString().toInt()
            }
        }

        override fun afterTextChanged(s: Editable) {
        }
    }

    private var multiWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if(s.toString() == "") {
                die.multiple = null
            } else {
                die.multiple = s.toString().toInt()
            }
        }

        override fun afterTextChanged(s: Editable) {
        }
    }
    //The method for checking if the sides or multiple properties of the die are 0 or null.
    //this is to stop the roll from crashing if either value is 0 or null
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

    //the overriden onStop method is used to save the die's values to preferences.
    //I found onStop worked best for this but onDestroy would probably work too
    override fun onStop() {
        super.onStop()
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putString("die", die.toString())
            apply()
        }
    }
}