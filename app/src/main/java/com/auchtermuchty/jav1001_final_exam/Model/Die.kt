package com.auchtermuchty.jav1001_final_exam.Model

import kotlin.random.Random

class Die(val sides: Int, val multiple: Int, val trueCount: Boolean) {
    fun roll(): String{
        return if(trueCount){
            (Random.nextInt(0, sides) * multiple).toString()
        } else {
            (Random.nextInt(1, sides) * multiple).toString()
        }
    }
}