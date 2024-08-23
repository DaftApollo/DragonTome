package com.moke.dragontome.data

import android.content.Context

interface AppContainer {
    val spellsRepository: SpellsRepository
}


class AppDataContainer(private val context: Context) : AppContainer {
    override val spellsRepository: SpellsRepository by lazy {
        OfflineSpellRepository(SpellDatabase.getDatabase(context).spellDao())
    }
}