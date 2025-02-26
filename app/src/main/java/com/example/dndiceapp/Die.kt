package com.example.dndiceapp

class Die(var sideCount: Byte = 4) {

    var dieType: String = "d$sideCount"
    var bAdvantage: Boolean = false


    fun roll(): Int {
        var result  = 0
        if(bAdvantage){
            val firstRoll = (1..sideCount).random()
            val secondRoll = (1..sideCount).random()
            result = maxOf(firstRoll, secondRoll)
        }else{
            result = (1..sideCount).random()
        }

        return result

    }


}
