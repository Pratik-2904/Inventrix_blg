package com.pss_dev.inventrix_blg.screens.navbarScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.Modifier.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.pss_dev.inventrix_blg.data.model.Party
import com.pss_dev.inventrix_blg.screens.CommonScreenLayout
import com.pss_dev.inventrix_blg.viewModel.PartyViewModel
import java.util.UUID

@Composable
fun PartiesScreen(
    navController: NavController = rememberNavController(),
    viewModel: PartyViewModel = hiltViewModel()
) {
    val parties by viewModel.parties.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    // State variables
    var showAddPartySheet by remember { mutableStateOf(false) }
    var selectedParty by remember { mutableStateOf<Party?>(null) }
    var showEditPartySheet by remember { mutableStateOf(false) }

    CommonScreenLayout(navController) { paddingValues, _ ->
        PartiesContent(
            paddingValues = paddingValues,
            parties = parties,
            searchQuery = searchQuery,
            onSearchQueryChange = { viewModel.updateSearchQuery(it) },
            onAddClick = { showAddPartySheet = true },
            onPartyClick = { party -> selectedParty = party },
            onPartyUpdate = { party ->
                viewModel.updateParty(party)
                showEditPartySheet = false
            },
            showAddPartySheet = showAddPartySheet,
            onDismissAddSheet = { showAddPartySheet = false },
            onSaveNewParty = { party ->
                viewModel.addParty(party)
                showAddPartySheet = false
            },
            selectedParty = selectedParty,
            onDismissPartyDetails = { selectedParty = null },
            showEditPartySheet = showEditPartySheet,
            onEditClick = { showEditPartySheet = true },
            onDismissEditSheet = { showEditPartySheet = false }
        )
    }
}

@Composable
private fun PartiesContent(
    paddingValues: PaddingValues,
    parties: List<Party>,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onPartyClick: (Party) -> Unit,
    onPartyUpdate: (Party) -> Unit,
    showAddPartySheet: Boolean,
    onDismissAddSheet: () -> Unit,
    onSaveNewParty: (Party) -> Unit,
    selectedParty: Party?,
    onDismissPartyDetails: () -> Unit,
    showEditPartySheet: Boolean,
    onEditClick: () -> Unit,
    onDismissEditSheet: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFF1C1B1F)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // Search Bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFF2D2D2D),
                        unfocusedContainerColor = Color(0xFF2D2D2D),
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = Color(0xFF3D5AFE)
                    ),
                    placeholder = { Text("Search parties", color = Color.Gray) },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Gray
                        )
                    }
                )

                // Parties List
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(
                        items = parties,
                        key = { it.id }
                    ) { party ->
                        PartyItem(
                            party = party,
                            onClick = { onPartyClick(party) }
                        )
                    }
                }
            }

            // Add Party FAB
            FloatingActionButton(
                onClick = onAddClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = Color(0xFFE91E63)
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Party",
                    tint = Color.White
                )
            }

            // Modal Sheets
            if (showAddPartySheet) {
                AddPartyBottomSheet(
                    onDismiss = onDismissAddSheet,
                    onSave = onSaveNewParty
                )
            }

            selectedParty?.let { party ->
                PartyDetailsBottomSheet(
                    party = party,
                    onDismiss = onDismissPartyDetails,
                    onEditClick = onEditClick
                )
            }

            if (showEditPartySheet && selectedParty != null) {
                EditPartyBottomSheet(
                    party = selectedParty,
                    onDismiss = onDismissEditSheet,
                    onSave = onPartyUpdate
                )
            }
        }
    }
}

@Composable
fun PartyItem(
    party: Party,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
        color = Color(0xFF2D2D2D),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = party.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = party.contactPerson,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = party.phoneNumber,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFE91E63)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = party.address,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartyDetailsBottomSheet(
    party: Party,
    onDismiss: () -> Unit,
    onEditClick: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF2D2D2D)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Party Details",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color(0xFFE91E63)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            DetailItem("Name", party.name)
            DetailItem("Contact Person", party.contactPerson)
            DetailItem("Phone", party.phoneNumber)
            DetailItem("Email", party.email)
            DetailItem("Address", party.address)
            DetailItem("GST Number", party.gstNumber ?: "")

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPartyBottomSheet(
    party: Party,
    onDismiss: () -> Unit,
    onSave: (Party) -> Unit
) {
    var name by remember { mutableStateOf(party.name) }
    var contactPerson by remember { mutableStateOf(party.contactPerson) }
    var phoneNumber by remember { mutableStateOf(party.phoneNumber) }
    var email by remember { mutableStateOf(party.email) }
    var address by remember { mutableStateOf(party.address) }
    var gstNumber by remember { mutableStateOf(party.gstNumber) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF2D2D2D)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Edit Party",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1C1B1F),
                    unfocusedContainerColor = Color(0xFF1C1B1F),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = contactPerson,
                onValueChange = { contactPerson = it },
                label = { Text("Contact Person") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1C1B1F),
                    unfocusedContainerColor = Color(0xFF1C1B1F),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                )
            )

            // Add similar fields for phone, email, address, GST number

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onSave(
                        party.copy(
                            name = name,
                            contactPerson = contactPerson,
                            phoneNumber = phoneNumber,
                            email = email,
                            address = address,
                            gstNumber = gstNumber
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63)
                )
            ) {
                Text("Save Changes")
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
private fun DetailItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPartyBottomSheet(
    onDismiss: () -> Unit,
    onSave: (Party) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var contactPerson by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var gstNumber by remember { mutableStateOf("") }

    // Validation states
    var nameError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }

    // Validation function
    fun validateInputs(): Boolean {
        nameError = name.trim().isEmpty()
        phoneError = phoneNumber.trim().isEmpty() || !phoneNumber.matches(Regex("^[0-9]{10}$"))
        return !nameError && !phoneError
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF2D2D2D)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Add New Party",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Name Field
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    nameError = false
                },
                label = { Text("Party Name*") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1C1B1F),
                    unfocusedContainerColor = Color(0xFF1C1B1F),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFFE91E63),
                    unfocusedLabelColor = Color.Gray,
                    errorContainerColor = Color(0xFF1C1B1F)
                ),
                isError = nameError,
                supportingText = {
                    if (nameError) {
                        Text("Name is required", color = MaterialTheme.colorScheme.error)
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Contact Person Field
            OutlinedTextField(
                value = contactPerson,
                onValueChange = { contactPerson = it },
                label = { Text("Contact Person") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1C1B1F),
                    unfocusedContainerColor = Color(0xFF1C1B1F),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFFE91E63),
                    unfocusedLabelColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Phone Number Field
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = {
                    phoneNumber = it
                    phoneError = false
                },
                label = { Text("Phone Number*") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1C1B1F),
                    unfocusedContainerColor = Color(0xFF1C1B1F),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFFE91E63),
                    unfocusedLabelColor = Color.Gray,
                    errorContainerColor = Color(0xFF1C1B1F)
                ),
                isError = phoneError,
                supportingText = {
                    if (phoneError) {
                        Text(
                            "Enter valid 10-digit phone number",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Email Field
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1C1B1F),
                    unfocusedContainerColor = Color(0xFF1C1B1F),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFFE91E63),
                    unfocusedLabelColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Address Field
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 2,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1C1B1F),
                    unfocusedContainerColor = Color(0xFF1C1B1F),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFFE91E63),
                    unfocusedLabelColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // GST Number Field
            OutlinedTextField(
                value = gstNumber,
                onValueChange = { gstNumber = it },
                label = { Text("GST Number") },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFF1C1B1F),
                    unfocusedContainerColor = Color(0xFF1C1B1F),
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedLabelColor = Color(0xFFE91E63),
                    unfocusedLabelColor = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Save Button
            Button(
                onClick = {
                    if (validateInputs()) {
                        onSave(
                            Party(
                                id = UUID.randomUUID().toString(),
                                name = name.trim(),
                                contactPerson = contactPerson.trim(),
                                phoneNumber = phoneNumber.trim(),
                                email = email.trim(),
                                address = address.trim(),
                                gstNumber = gstNumber.trim()
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63)
                )
            ) {
                Text(
                    text = "Save Party",
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
