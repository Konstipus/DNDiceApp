package com.example.dndiceapp

class Die(var sideCount: Byte = 4) {

    var dieType: String = "d$sideCount"


    fun roll(): Byte {
        val result: Byte = (1.toByte()..sideCount).random().toByte()  // Cast result to Byte
        println("Rolling ${dieType} for $result")
        return result

    }


}
