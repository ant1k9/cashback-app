package com.example.mycashback

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mycashback.storage.BanksFileStorageProxy

class BankGridItemAdapter(private val banksFileStorageProxy: BanksFileStorageProxy) :
    RecyclerView.Adapter<BankGridItemAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        val button: Button = view.findViewById(R.id.remove)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.bankgriditem, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = banksFileStorageProxy.get(position.toString())
        viewHolder.button.setOnClickListener{
            notifyItemRemoved(position)
            banksFileStorageProxy.remove(position.toString())
        }
    }

    override fun getItemCount(): Int {
        return banksFileStorageProxy.size()
    }

    override fun getItemId(position: Int): Long = position.toLong()
}
