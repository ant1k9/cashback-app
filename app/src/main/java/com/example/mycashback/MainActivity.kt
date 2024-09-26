package com.example.mycashback

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycashback.adapters.CategoryGridItemAdapter
import com.example.mycashback.storage.BanksFileStorageProxy
import com.example.mycashback.storage.CategoriesFileStorageProxy
import java.time.LocalDate
import kotlin.math.absoluteValue

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

    private fun buttonMonthChangedHandler(month: Int, year: Int): (View) -> Unit {
        return fun(_: View) {
            val intent = Intent(this, MainActivity::class.java)
            val bundle = Bundle()
            bundle.putInt("month", month)
            bundle.putInt("year", year)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()
        }
    }

    private fun renderCategoryList(
        categoriesFileStorageProxy: CategoriesFileStorageProxy,
        banksFileStorageProxy: BanksFileStorageProxy
    ) {
        val categoryGridItemAdapter = CategoryGridItemAdapter(
            applicationContext, layoutInflater, categoriesFileStorageProxy, banksFileStorageProxy,
            categoriesFileStorageProxy.list()
        )

        val recyclerView: RecyclerView = findViewById(R.id.categories_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = categoryGridItemAdapter
    }

    private fun renderCreateButton(
        categoriesFileStorageProxy: CategoriesFileStorageProxy,
        banksFileStorageProxy: BanksFileStorageProxy
    ) {
        val createButton = findViewById<Button>(R.id.add_category_button)
        createButton.setOnClickListener {
            val addCategoryTextView = findViewById<TextView>(R.id.add_category_text_field)
            val category = addCategoryTextView.text
            Toast.makeText(applicationContext, "Категория $category сохранена", Toast.LENGTH_LONG)
                .show()
            addCategoryTextView.text = ""

            categoriesFileStorageProxy.add(category.toString(), "")

            renderCategoryList(categoriesFileStorageProxy, banksFileStorageProxy)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        val now = LocalDate.now()
        val month = intent.extras?.getInt("month", now.monthValue) ?: now.monthValue
        val year = intent.extras?.getInt("year", now.year.absoluteValue) ?: now.year.absoluteValue

        super.onCreate(savedInstanceState)
        setContentView(R.layout.listview)

        setActionBar(findViewById(R.id.toolbar))

        val categoriesFileName = filesDir.path + "/" + "categories-$year-$month.json"
        val categoriesFileStorageProxy = CategoriesFileStorageProxy(categoriesFileName)

        val banksFileName = filesDir.path + "/" + "banks.json"
        val banksFileStorageProxy = BanksFileStorageProxy(banksFileName)

        val previousMonthButton = findViewById<Button>(R.id.previous_month)
        previousMonthButton.setOnClickListener(
            buttonMonthChangedHandler(
                if (month == 1) 12 else (month - 1),
                if (month == 1) year - 1 else year
            )
        )

        val nextMonthButton = findViewById<Button>(R.id.next_month)
        nextMonthButton.setOnClickListener(
            buttonMonthChangedHandler(
                if (month == 12) 1 else (month + 1),
                if (month == 12) year + 1 else year
            )
        )

        val currentMonthTextView = findViewById<TextView>(R.id.current_month)
        currentMonthTextView.text = "${month.toString().padStart(2, '0')}-$year"

        renderCategoryList(categoriesFileStorageProxy, banksFileStorageProxy)
        renderCreateButton(categoriesFileStorageProxy, banksFileStorageProxy)
    }
}