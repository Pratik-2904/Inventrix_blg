package com.pss_dev.inventrix_blg.viewModel.InvoiceViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pss_dev.inventrix_blg.data.model.InvoiceCustomer
import javax.inject.Inject

class HandleCustomerVM @Inject constructor() :ViewModel() {
    val _customer = MutableLiveData<InvoiceCustomer>()
    val customer = _customer

}