package com.pss_dev.inventrix_blg.data.localDb

import com.pss_dev.inventrix_blg.data.localDb.dao.PartyDao
import com.pss_dev.inventrix_blg.data.model.Party
import com.pss_dev.inventrix_blg.data.model.toDomain
import com.pss_dev.inventrix_blg.data.model.toEntity
import com.pss_dev.inventrix_blg.repository.PartyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PartyRepositoryImpl @Inject constructor(
    private val partyDao: PartyDao
) : PartyRepository {
    override suspend fun getAllParties(): List<Party> {
        return partyDao.getAllParties().map { it.toDomain() }
    }

    override suspend fun searchParties(query: String): List<Party> {
        return partyDao.searchParties(query).map { it.toDomain() }
    }

    override suspend fun getPartyById(id: String): Party? {
        return partyDao.getPartyById(id)?.toDomain()
    }

    override suspend fun addParty(party: Party) {
        partyDao.insertParty(party.toEntity())
    }

    override suspend fun updateParty(party: Party) {
        partyDao.updateParty(party.toEntity())
    }

    override suspend fun deleteParty(id: String) {
        partyDao.deleteParty(id)
    }
}
