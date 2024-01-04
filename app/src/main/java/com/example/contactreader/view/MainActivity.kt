package com.example.contactreader.view

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import android.Manifest
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.drmoapp.R
import com.example.contactreader.model.Contact
import com.example.contactreader.repository.ContactsRepository
import com.example.contactreader.viewmodel.ContactsViewModel
import com.example.contactreader.viewmodel.ContactsViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, ContactsViewModelFactory(ContactsRepository(this)))
            .get(ContactsViewModel::class.java)

        val readContactsButton = findViewById<Button>(R.id.btn1)
        readContactsButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    1
                )
            } else {
                viewModel.loadContacts()
            }
        }

        // Observe changes in contacts data
        viewModel.contacts.observe(this, { contacts ->
            openSecondActivity(contacts)
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            viewModel.loadContacts()
        }
    }

    private fun openSecondActivity(contactsList: List<Contact>) {
        val intent = Intent(this, SecondActivity::class.java).apply {
            putExtra("CONTACTS_LIST", ArrayList(contactsList))
        }
        startActivity(intent)
    }
}
