package com.example.dragontome.data

import android.util.Log
import androidx.annotation.IntRange
import net.objecthunter.exp4j.ExpressionBuilder
import org.checkerframework.checker.regex.qual.Regex
import kotlin.random.Random
import kotlin.random.asJavaRandom

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
                Log.d("debug", "DiceAmount: $diceAmount, DieFaces: $dieFaces")
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
        Log.d("debug", roll)

        try {
            val result = ExpressionBuilder(roll).build().evaluate()
            roll = roll + " = $result"
        }catch (e:Exception){
        }


        return roll
    }

}