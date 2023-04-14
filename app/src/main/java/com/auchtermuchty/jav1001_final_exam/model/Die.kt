package com.auchtermuchty.jav1001_final_exam.model

import kotlin.random.Random

//The class for the die.  Implements having the roll start at 0 and having the roll result be
//in multiples.
class Die(var sides: Int?, var multiple: Int?, var trueCount: Boolean) {
    fun roll(): String{
        //null check
        return if(sides == null || multiple == null) {
            return "-1"
        }
        //using https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.random/-random/
        else if(trueCount){
            (Random.nextInt(0, sides!!) * multiple!!).toString()
        }
        else {
            (Random.nextInt(1, (sides!! + 1)) * multiple!!).toString()
        }
    }

    //I overrode the toString method to help with getting the die's information from prefences
    override fun toString(): String {
        return ("$sides,$multiple,$trueCount")
    }
}

//This function gets a die from a string from the toString method.  With this a die can be saved and
//retrieved using only 1 string in the preferences.
fun getDieFromString(dieString: String) : Die {
    val dieList = dieString.split(",")
    val sides = dieList[0].toInt()
    val multiple = dieList[1].toInt()
    val trueCount = dieList[2].toBoolean()
    return Die(sides, multiple, trueCount)
}