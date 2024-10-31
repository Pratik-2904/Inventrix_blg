package com.pss_dev.inventrix_blg.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pss_dev.inventrix_blg.data.model.Party
import com.pss_dev.inventrix_blg.repository.PartyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class PartyViewModel @Inject constructor(
    private val partyRepository: PartyRepository
) : ViewModel() {

    private val _parties = MutableStateFlow<List<Party>>(emptyList())
    val parties = _parties.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    init {
        loadParties()
        // Setup search functionality
        viewModelScope.launch {
            searchQuery
                .debounce(300L)
                .distinctUntilChanged()
                .collect { query ->
                    if (query.isEmpty()) {
                        loadParties()
                    } else {
                        searchParties(query)
                    }
                }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    private fun loadParties() {
        viewModelScope.launch {
            try {
                _loading.value = true
                _parties.value = partyRepository.getAllParties()
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to load parties: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    private fun searchParties(query: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _parties.value = partyRepository.searchParties(query)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to search parties: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addParty(party: Party) {
        viewModelScope.launch {
            try {
                _loading.value = true
                partyRepository.addParty(party)
                loadParties() // Reload the list after adding
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to add party: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateParty(party: Party) {
        viewModelScope.launch {
            try {
                _loading.value = true
                partyRepository.updateParty(party)
                loadParties() // Reload the list after updating
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to update party: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteParty(partyId: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                partyRepository.deleteParty(partyId)
                loadParties() // Reload the list after deletion
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Failed to delete party: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}
