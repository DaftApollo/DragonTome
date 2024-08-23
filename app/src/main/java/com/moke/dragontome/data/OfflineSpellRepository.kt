package com.moke.dragontome.data

class OfflineSpellRepository(private val spellDao: SpellDao) : SpellsRepository {
    override suspend fun getAllSpellsStream(): List<Spell> = spellDao.getAllSpells()

    override fun getSpellStream(id: Int): Spell? = spellDao.getSpell(id)

    override suspend fun insertSpell(spell: Spell) = spellDao.insert(spell)

    override suspend fun deleteSpell(spell: Spell) = spellDao.delete(spell)

    override suspend fun updateSpell(spell: Spell) = spellDao.update(spell)
}