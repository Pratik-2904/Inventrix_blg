package com.pss_dev.inventrix_blg.data.localDb.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.pss_dev.inventrix_blg.data.model.Party
import com.pss_dev.inventrix_blg.data.model.PartyEntity

@Dao
interface PartyDao {
    @Query("SELECT * FROM parties ORDER BY name ASC")
    suspend fun getAllParties(): List<PartyEntity>

    @Query("SELECT * FROM parties WHERE name LIKE :query OR contactPerson LIKE :query OR phoneNumber LIKE :query")
    suspend fun searchParties(query: String): List<PartyEntity>

    @Query("SELECT * FROM parties WHERE id = :id")
    suspend fun getPartyById(id: String): PartyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertParty(party: PartyEntity)

    @Update
    suspend fun updateParty(party: PartyEntity)

    @Query("DELETE FROM parties WHERE id = :id")
    suspend fun deleteParty(id: String)
}
