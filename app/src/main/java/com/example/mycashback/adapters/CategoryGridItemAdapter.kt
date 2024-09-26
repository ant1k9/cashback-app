package com.example.mycashback.adapters

import android.content.Context
import android.view.Gravity
import com.example.mycashback.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mycashback.storage.BanksFileStorageProxy
import com.example.mycashback.storage.CategoriesFileStorageProxy

class CategoryGridItemAdapter(
    private val applicationContext: Context,
    private val layoutInflater: LayoutInflater,
    private val categoriesFileStorageProxy: CategoriesFileStorageProxy,
    private val banksFileStorageProxy: BanksFileStorageProxy,
    private val categories: List<String>,
) :
    RecyclerView.Adapter<CategoryGridItemAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.category)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.categoriesgriditem, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.button.text = categories[position]

        viewHolder.button.setOnClickListener {
            val category = (it as Button).text
            val bank = categoriesFileStorageProxy.get(category.toString())
            val toastMessage = when (bank) {
                "" -> "Для категории $category нет выбранного банка в этом месяце"
                else -> "Для категории $category лучший cashback в банке $bank"
            }
            Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_LONG).show()
        }

        viewHolder.button.setOnLongClickListener {
            val popupView = layoutInflater.inflate(R.layout.choicepopup, null)

            val choiceGridItemAdapter = ChoiceGridItemAdapter(
                (it as Button).text.toString(),
                categoriesFileStorageProxy,
                banksFileStorageProxy,
            )

            val recyclerView: RecyclerView = popupView.findViewById(R.id.choice_list)
            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
            recyclerView.adapter = choiceGridItemAdapter

            val popupWindow = PopupWindow(popupView)
            popupWindow.width = 1000
            popupWindow.height = 2000

            choiceGridItemAdapter.setPopupWindow(popupWindow)

            val cancelButton = popupView.findViewById<Button>(R.id.cancel_button)
            cancelButton.setOnClickListener { popupWindow.dismiss() }

            popupWindow.showAsDropDown(it, 0, 0, Gravity.BOTTOM)
            true
        }
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun getItemId(position: Int): Long = position.toLong()
}
