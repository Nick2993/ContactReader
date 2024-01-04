package com.example.contactreader.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactreader.model.Contact
import com.example.contactreader.repository.ContactsRepository

class ContactsViewModel(private val contactsRepository: ContactsRepository) : ViewModel() {
    val contacts = MutableLiveData<List<Contact>>()

    fun loadContacts() {
        contacts.value = contactsRepository.getContacts()
    }
}

class ContactsViewModelFactory(private val repository: ContactsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            return ContactsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
