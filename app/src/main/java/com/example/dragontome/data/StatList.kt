package com.example.dragontome.data

import kotlinx.serialization.Serializable

@Serializable
class StatList(
    var statList:List<StatEntry> = listOf(StatEntry()),
    var usesBaseStat: Boolean = true,
    var baseValue: Int = 0
) {



    fun sumEntries(): Int {
        var total:Int = if(usesBaseStat) baseValue else 0
        statList.forEach {
            total += it.stat
        }
        return total
    }
}