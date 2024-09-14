package com.example.mycashback

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listview)

        setActionBar(findViewById(R.id.toolbar))

        val offers = mapOf(
            R.string.products to R.string.tbank,
            R.string.restaurants to R.string.ozon,
            R.string.other to R.string.tbank,
            R.string.clothing to R.string.sber,
            R.string.transport to R.string.sber,
            R.string.dental to R.string.mkb,
            R.string.drugstore to R.string.tbank,
            R.string.electronics to R.string.vtb,
            R.string.kids to R.string.vtb,
            R.string.entertainment to R.string.tbank,
            R.string.flowers to R.string.ozon,
            R.string.housing to R.string.alpha,
            R.string.taxi to R.string.mkb
        ).map {
            resources.getString(it.key) to resources.getString(it.value)
        }.toMap()

        listOf(
            R.id.cashbackButton1,
            R.id.cashbackButton2,
            R.id.cashbackButton3,
            R.id.cashbackButton4,
            R.id.cashbackButton5,
            R.id.cashbackButton6,
            R.id.cashbackButton7,
            R.id.cashbackButton8,
            R.id.cashbackButton9,
            R.id.cashbackButton10,
            R.id.cashbackButton11,
            R.id.cashbackButton12,
            R.id.cashbackButton13,
        ).forEach {
            val button = findViewById<Button>(it)
            button.setOnClickListener { b ->
                val category = findViewById<Button>(b.id).text
                val bank = offers[category]
                Toast.makeText(
                    applicationContext,
                    "Для категории $category лучший cashback в банке $bank",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}