package com.moke.dragontome.state

import com.moke.dragontome.data.Spell
import com.moke.dragontome.data.SpellDatabase

data class AppUiState(
    val spellList: List<Spell>
)
