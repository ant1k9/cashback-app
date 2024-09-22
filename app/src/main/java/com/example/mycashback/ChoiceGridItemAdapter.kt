package com.example.mycashback

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.example.mycashback.storage.BanksFileStorageProxy
import com.example.mycashback.storage.CategoriesFileStorageProxy

class ChoiceGridItemAdapter(
    private val category: String,
    private val categoriesFileStorageProxy: CategoriesFileStorageProxy,
    private val banksFileStorageProxy: BanksFileStorageProxy,
) :
    RecyclerView.Adapter<ChoiceGridItemAdapter.ViewHolder>() {

    var parentWindow: PopupWindow = PopupWindow()
    fun setPopupWindow(popupWindow: PopupWindow) {
        this.parentWindow = popupWindow
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.choose)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.choicegriditem, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.button.text = banksFileStorageProxy.get(position.toString())
        viewHolder.button.setOnClickListener {
            notifyItemRemoved(position)
            categoriesFileStorageProxy.add(category, viewHolder.button.text.toString())
            parentWindow.dismiss()
        }
    }

    override fun getItemCount(): Int {
        return banksFileStorageProxy.size()
    }

    override fun getItemId(position: Int): Long = position.toLong()
}
