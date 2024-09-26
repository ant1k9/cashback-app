package com.example.mycashback

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycashback.adapters.BankGridItemAdapter
import com.example.mycashback.storage.BanksFileStorageProxy

class BanksActivity : ComponentActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.main_page -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun renderBanksList(banksFileStorageProxy: BanksFileStorageProxy) {
        val bankGridItemAdapter = BankGridItemAdapter(banksFileStorageProxy)

        val recyclerView: RecyclerView = findViewById(R.id.banks_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = bankGridItemAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.banksview)

        setActionBar(findViewById(R.id.banks_toolbar))

        val banksFileName = filesDir.path + "/" + "banks.json"
        val banksFileStorageProxy = BanksFileStorageProxy(banksFileName)
        renderBanksList(banksFileStorageProxy)

        val createButton = findViewById<Button>(R.id.add_bank_button)
        createButton.setOnClickListener {
            val addBankTextView = findViewById<TextView>(R.id.add_bank_text_field)
            val bank = addBankTextView.text
            Toast.makeText(applicationContext, "Банк $bank сохранен", Toast.LENGTH_LONG).show()
            addBankTextView.text = ""

            banksFileStorageProxy.add(bank.toString())

            renderBanksList(banksFileStorageProxy)
        }
    }
}