package com.example.mycashback

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycashback.storage.BanksFileStorageProxy
import com.example.mycashback.storage.CategoriesFileStorageProxy


class MainActivity : ComponentActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.banks -> {
                val intent = Intent(this, BanksActivity::class.java)
                startActivity(intent)
                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listview)

        setActionBar(findViewById(R.id.toolbar))

        val categoriesFileName = filesDir.path + "/" + "categories-2024-09.json"
        val categoriesFileStorageProxy = CategoriesFileStorageProxy(categoriesFileName)

        val banksFileName = filesDir.path + "/" + "banks.json"
        val banksFileStorageProxy = BanksFileStorageProxy(banksFileName)

        /*
        val offers = listOf(
            R.string.products,
            R.string.restaurants,
            R.string.other,
            R.string.clothing,
            R.string.transport,
            R.string.dental,
            R.string.drugstore,
            R.string.electronics,
            R.string.kids,
            R.string.entertainment,
            R.string.flowers,
            R.string.housing,
            R.string.taxi
        ).map { resources.getString(it) }
         */

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
                val bank = categoriesFileStorageProxy.get(category.toString())
                val toastMessage = when (bank) {
                    "" -> "Для категории $category нет выбранного банка в этом месяце"
                    else -> "Для категории $category лучший cashback в банке $bank"
                }
                Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_LONG).show()
            }

            button.setOnLongClickListener{ b ->
                val popupView = layoutInflater.inflate(R.layout.choicepopup, null)

                val choiceGridItemAdapter = ChoiceGridItemAdapter(
                    button.text.toString(),
                    categoriesFileStorageProxy,
                    banksFileStorageProxy,
                )

                val recyclerView: RecyclerView = popupView.findViewById(R.id.choice_list)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = choiceGridItemAdapter

                val popupWindow = PopupWindow(popupView)
                popupWindow.width = 1000
                popupWindow.height = 2000

                choiceGridItemAdapter.setPopupWindow(popupWindow)

                val cancelButton = popupView.findViewById<Button>(R.id.cancel_button)
                cancelButton.setOnClickListener{ popupWindow.dismiss() }

                popupWindow.showAsDropDown(button, 0, 0, Gravity.BOTTOM)
                true
            }
        }
    }
}