package com.auchtermuchty.jav1001_final_exam.Model

import kotlin.random.Random


class Die(var sides: Int?, var multiple: Int?, var trueCount: Boolean) {
    fun roll(): String{
        //using https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.random/-random/
        return if(sides == null) {
            return "-1"
        }
        else if(multiple == null) {
            return "-2"
        }
        else if(trueCount){
            (Random.nextInt(0, sides!!) * multiple!!).toString()
        }
        else {
            (Random.nextInt(1, (sides!! + 1)) * multiple!!).toString()
        }
    }

    override fun toString(): String {
        return ("$sides,$multiple,$trueCount")
    }
}

fun getDieFromString(dieString: String) : Die {
    val dieList = dieString.split(",")
    val sides = dieList[0].toInt()
    val multiple = dieList[1].toInt()
    val trueCount = dieList[2].toBoolean()
    return Die(sides, multiple, trueCount)
}