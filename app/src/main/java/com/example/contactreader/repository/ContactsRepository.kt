package com.example.contactreader.repository

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import com.example.contactreader.model.Contact

class ContactsRepository(private val context: Context) {

    @SuppressLint("Range")
    fun getContacts(): List<Contact> {
        val contactsList = mutableListOf<Contact>()
        val contactsCursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null
        )
        while (contactsCursor?.moveToNext() == true) {
            val name =
                contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber =
                contactsCursor.getString(contactsCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            contactsList.add(Contact(name, phoneNumber))
        }
        contactsCursor?.close()
        return contactsList
    }
}
