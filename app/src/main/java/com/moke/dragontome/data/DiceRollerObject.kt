package com.moke.dragontome.data

import net.objecthunter.exp4j.ExpressionBuilder

object DiceRollerObject {

    fun detectRoll(message:String):Boolean{
        if (message.startsWith(prefix = "/roll", ignoreCase = true) ||
            message.startsWith("/r", ignoreCase = true) ||
            message.startsWith(".r", ignoreCase = true) ||
            message.startsWith(".roll", ignoreCase = true) ||
            message.startsWith("\\r", ignoreCase = true) ||
            message.startsWith("\\roll", ignoreCase = true)){
            return true
        }
        else{
            return false
        }
    }

    fun getRollResult(dieFaces:Int):Int{
        val randomvalue = IntRange(1, dieFaces).random()
        return randomvalue
    }

    fun validateWholeMessage(string:String):Boolean{
        return !Regex(pattern = "[^0-9+\\-(-)d\\s*\\/]").containsMatchIn(string) && Regex(pattern = "(?:d\\d)").containsMatchIn(string)
    }

    fun doRoll(string:String):String{
        var roll:String = string
        roll = roll.replace(Regex(pattern = "r|o|l|\\/|\\\\|\\."), "")
        if (validateWholeMessage(roll)){
            var matches = Regex(pattern = "(?:\\d*d\\d+)").findAll(string)

            for (match in matches){

                val dIndex = match.value.indexOf('d')
                var diceAmount: Int
                var dieFaces:Int

                try {
                    diceAmount = match.value.slice(0..dIndex-1).toInt()
                }catch (e:Exception){diceAmount = 1}

                dieFaces = match.value.slice(dIndex + 1..match.value.length - 1).toInt()

                var rollSequence = "("

                if(diceAmount > 100){
                    diceAmount = 100
                }
                for(i in 0..diceAmount - 1){
                    rollSequence = rollSequence + getRollResult(dieFaces).toString()
                    if(i < diceAmount - 1){
                        rollSequence = rollSequence + " + "
                    }
                    else{
                        rollSequence = rollSequence + ")"
                    }
                }

                roll = roll.replaceFirst(match.value, rollSequence)
            }
            try {
                val result = ExpressionBuilder(roll).build().evaluate().toInt()
                roll = roll + " = $result"
            }catch (e:Exception){
            }
            return roll
        } else{
            return "Error: tried rolling $roll"
        }



    }

}