package com.pss_dev.inventrix_blg.repository

import com.pss_dev.inventrix_blg.data.model.Party

interface PartyRepository {
    suspend fun getAllParties(): List<Party>
    suspend fun searchParties(query: String): List<Party>
    suspend fun getPartyById(id: String): Party?
    suspend fun addParty(party: Party)
    suspend fun updateParty(party: Party)
    suspend fun deleteParty(id: String)
}