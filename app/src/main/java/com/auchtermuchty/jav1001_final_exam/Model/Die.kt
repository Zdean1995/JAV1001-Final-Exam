package com.auchtermuchty.jav1001_final_exam.Model

import kotlin.random.Random

class Die(var sides: Int, var multiple: Int, var trueCount: Boolean) {
    fun roll(): String{
        //using https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.random/-random/
        return if(trueCount){
            (Random.nextInt(0, sides) * multiple).toString()
        } else {
            (Random.nextInt(1, (sides + 1)) * multiple).toString()
        }
    }
}